package com.maemresen.libs.jmh.utils.db.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PostgresDbExecutor implements DbExecutor {

  private final String jdbcURL;
  private final String jdbcUsername;
  private final String jdbcPassword;

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
  }

  @Override
  public void importCSV(final InputStream inputStream, final String tableName) throws SQLException, IOException {
    final String copySql = "COPY " + tableName + " FROM STDIN WITH (FORMAT csv, HEADER true)";
    try (Connection connection = getConnection();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
      connection.setAutoCommit(false);
      try {
        executeCopy(connection, bufferedReader, copySql);
        connection.commit();
      } catch (final SQLException | IOException e) {
        connection.rollback();
        log.error("Exception while importing CSV", e);
        throw e;
      } finally {
        connection.setAutoCommit(true);
      }
    }
  }

  private void executeCopy(final Connection connection, final BufferedReader bufferedReader, final String copySql) throws SQLException, IOException {
    final PGConnection pgConnection = connection.unwrap(PGConnection.class);
    final CopyManager copyManager = pgConnection.getCopyAPI();
    copyManager.copyIn(copySql, bufferedReader);
  }

  @Override
  public void executeSQLFile(final InputStream inputStream) throws SQLException, IOException {
    try (Connection connection = getConnection();
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
         Statement statement = connection.createStatement()) {
      connection.setAutoCommit(false);
      try {
        executeSQL(reader, statement);
        connection.commit();
      } catch (final SQLException | IOException e) {
        connection.rollback();
        log.error("Exception while executing SQL", e);
        throw e;
      } finally {
        connection.setAutoCommit(true);
      }
    }
  }

  private void executeSQL(final BufferedReader reader, final Statement statement)
      throws SQLException, IOException {
    final StringBuilder sql = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      sql.append(line);
      if (line.trim().endsWith(";")) {
        statement.addBatch(sql.toString());
        sql.setLength(0);  // Reset the SQL string builder
      }
    }
    statement.executeBatch();
  }
}