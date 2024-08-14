package com.maemresen.querydsljpqqueries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@SpringBootTest
class QuerydslJpqQueriesApplicationTests {

  @TestConfiguration
  static class QuerydslJpqQueriesApplicationTestContextConfiguration {

    @ServiceConnection
    @Bean
    public PostgreSQLContainer postgreSQLContainer() {
      return new PostgreSQLContainer("postgres:13-alpine");
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(final EntityManager entityManager) {
      return new JPAQueryFactory(entityManager);
    }

    @Bean
    public SQLQueryFactory sqlQueryFactory(final DataSource dataSource) {
      return new SQLQueryFactory(queryDslConfiguration(), new SpringConnectionProvider(dataSource));
    }

    @Bean
    public Configuration queryDslConfiguration() {
      final SQLTemplates templates = PostgreSQLTemplates.builder().build();
      final Configuration configuration = new Configuration(templates);
      configuration.setExceptionTranslator(new SpringExceptionTranslator());
      return configuration;
    }
  }

  @Autowired
  private JPAQueryFactory jpaQueryFactory;

  @Autowired
  private SQLQueryFactory sqlQueryFactory;

  @Autowired
  private PersonRepository personRepository;

  private Person john;
  private Person jane;

  @BeforeAll
  void setUp() {
    assertNotNull(jpaQueryFactory);

    john = createPerson("John", "Doe");
    jane = createPerson("Jane", "Doe");
    personRepository.saveAll(Set.of(john, jane));
    assertEquals(2, personRepository.count());
  }

  private Person createPerson(final String firstName, final String lastName) {
    return Person.builder()
        .firstName(firstName)
        .lastName(lastName)
        .build();
  }

  @BeforeEach
  void setUpEach(final TestInfo testInfo) {
    System.out.println();
    System.out.println("Test: " + testInfo.getDisplayName());
  }

  @AfterEach
  void afterEach() {
    System.out.println();
  }


  @Test
  void testNamedQuery() {
    final Optional<Person> foundPerson = personRepository.findByFirstName(john.getFirstName());

    assertTrue(foundPerson.isPresent());
    assertEquals(john.getFirstName(), foundPerson.get().getFirstName());
  }

  @Test
  void testJpaQuery() {
    final Optional<Person> foundPerson = personRepository.findByJpa(john.getFirstName());

    assertTrue(foundPerson.isPresent());
    assertEquals(john.getFirstName(), foundPerson.get().getFirstName());
  }

  @Test
  void testQueryDslJpaQuery() {
    final JPAQuery<Person> query = jpaQueryFactory.selectFrom(QPerson.person).where(QPerson.person.firstName.eq(john.getFirstName()));

    System.out.println("QueryDSL generated query: " + query.toString().replaceAll("[\\n\\r]+", " ").replaceAll("\\s{2,}", " ").trim());

    final Person foundPerson = query.fetchOne();

    assertNotNull(foundPerson);
    assertEquals(john.getFirstName(), foundPerson.getFirstName());
  }

  @Test
  void testQueryDslSqlFactory() {
    final var qPerson = QPersonRawQuery.person;
    final SQLQuery<Person> query = sqlQueryFactory.select(Projections.bean(
            Person.class,
            qPerson.id,
            qPerson.firstName,
            qPerson.lastName
        ))
        .from(qPerson)
        .where(qPerson.firstName.eq(john.getFirstName()));

    System.out.println("QueryDSL generated query: " + query.toString().replaceAll("[\\n\\r]+", " ").replaceAll("\\s{2,}", " ").trim());

    final Person foundPerson = query.fetchOne();

    assertNotNull(foundPerson);
    assertEquals(john.getFirstName(), foundPerson.getFirstName());
  }
}