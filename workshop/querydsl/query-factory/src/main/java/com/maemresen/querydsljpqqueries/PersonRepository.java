package com.maemresen.querydsljpqqueries;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
  Optional<Person> findByFirstName(String firstName);

  @Query("select p from Person p where p.firstName = :firstName")
  Optional<Person> findByJpa(@Param("firstName") String firstName);
}
