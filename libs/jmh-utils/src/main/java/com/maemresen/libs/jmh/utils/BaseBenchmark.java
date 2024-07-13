package com.maemresen.libs.jmh.utils;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;


public abstract class BaseBenchmark {

  protected static PostgreSQLContainer<?> postgreSQLContainer;

  protected ConfigurableApplicationContext context;

  static {
    postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("test")
        .withUsername("test")
        .withPassword("test");
    postgreSQLContainer.start();
  }

  protected abstract Class<?> getSpringApplicationClass();

  @Setup(Level.Trial)
  public void setUp() {
    System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
    System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
    System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    context = SpringApplication.run(getSpringApplicationClass());
  }

  @TearDown(Level.Trial)
  public void tearDown() {
    context.close();
  }
}