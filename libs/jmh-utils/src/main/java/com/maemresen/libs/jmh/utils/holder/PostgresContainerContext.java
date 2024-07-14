package com.maemresen.libs.jmh.utils.holder;

import lombok.Builder;
import lombok.Getter;
import org.testcontainers.containers.PostgreSQLContainer;

@Getter
public class PostgresContainerContext
    extends JdbcContainerContext<PostgreSQLContainer<?>> {

  @Builder
  public PostgresContainerContext(final String databaseName, final String userName,
                                  final String password) {
    super(new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName(databaseName)
        .withUsername(userName)
        .withPassword(password));
  }
}