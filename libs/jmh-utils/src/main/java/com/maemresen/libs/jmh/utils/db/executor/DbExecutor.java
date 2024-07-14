package com.maemresen.libs.jmh.utils.db.executor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public interface DbExecutor {

  void importCSV(InputStream inputStream, String tableName) throws SQLException, IOException;

  void executeSQLFile(InputStream inputStream) throws SQLException, IOException;

}