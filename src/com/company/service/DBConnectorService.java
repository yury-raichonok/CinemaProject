package com.company.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectorService {

  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DB_URL =
      "jdbc:mysql://localhost:3306/cinema?useUnicode=true&serverTimezone=UTC&useSSL=false";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "root";

  private static DBConnectorService instance;

  private Connection connection;

  public static DBConnectorService getInstance() {
    if (instance == null) {
      instance = new DBConnectorService();
    }
    return instance;
  }

  public Connection openConnection() {
    try {
      Class.forName(JDBC_DRIVER);
      connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return connection;
  }

  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}