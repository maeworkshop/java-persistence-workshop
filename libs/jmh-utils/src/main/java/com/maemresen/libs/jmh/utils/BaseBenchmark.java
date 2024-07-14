package com.maemresen.libs.jmh.utils;

import com.maemresen.libs.jmh.utils.db.executor.DbExecutor;
import com.maemresen.libs.jmh.utils.db.executor.PostgresDbExecutor;
import com.maemresen.libs.jmh.utils.holder.JdbcContainerContext;
import com.maemresen.libs.jmh.utils.holder.PostgresContainerContext;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseBenchmark {

  protected static final JdbcContainerContext<?> jdbcContainerContext = createContainerContext();

  static {
    log.info("Starting JDBC container context...");
    jdbcContainerContext.start();
    log.info("JDBC container context started.");
  }

  private static PostgresContainerContext createContainerContext() {
    return PostgresContainerContext.builder()
        .databaseName("test")
        .userName("test")
        .password("test")
        .build();
  }

  protected ConfigurableApplicationContext context;

  protected abstract Class<?> getSpringApplicationClass();

  @Setup(Level.Trial)
  public final void setUp() throws SQLException, IOException {
    log.info("Running setup for benchmark...");
    beforeSetup();
    final DbExecutor dbExecutor = createDatabaseExecutor();
    log.info("Executing SQL file...");
    executeSQLFile(dbExecutor, "formula1.sql");
    log.info("Importing CSV file...");
    importCSV(dbExecutor, "formula1/circuits.csv", "circuits");
    context = SpringApplication.run(getSpringApplicationClass());
    log.info("Spring application context started.");
    afterSetup();
    log.info("Setup for benchmark completed.");
  }

  @TearDown(Level.Trial)
  public final void tearDown() {
    log.info("Running teardown for benchmark...");
    if (context != null) {
      context.close();
      log.info("Spring application context closed.");
    }
    afterTearDown();
    log.info("Teardown for benchmark completed.");
  }

  protected void beforeSetup() {
    // default implementation
  }

  protected void afterSetup() {
    // default implementation
  }

  protected void afterTearDown() {
    // default implementation
  }

  private void executeSQLFile(final DbExecutor databaseExecutor, final String sqlFilePath)
      throws SQLException, IOException {
    Resource resource = new ClassPathResource(sqlFilePath);
    try (InputStream inputStream = resource.getInputStream()) {
      databaseExecutor.executeSQLFile(inputStream);
      log.info("Executed SQL file: {}", sqlFilePath);
    }
  }

  private void importCSV(final DbExecutor databaseExecutor, final String csvFilePath,
                         final String tableName) throws SQLException, IOException {
    Resource resource = new ClassPathResource(csvFilePath);
    try (InputStream inputStream = resource.getInputStream()) {
      databaseExecutor.importCSV(inputStream, tableName);
      log.info("Imported CSV file: {} into table: {}", csvFilePath, tableName);
    }
  }

  private DbExecutor createDatabaseExecutor() {
    log.info("Creating Database Executor with URL: {}", jdbcContainerContext.getJdbcUrl());
    return new PostgresDbExecutor(
        jdbcContainerContext.getJdbcUrl(),
        jdbcContainerContext.getUsername(),
        jdbcContainerContext.getPassword()
    );
  }
}