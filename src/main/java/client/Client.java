package client;

import java.sql.*;

public class Client {

  private Connection connection;

  public Client(String connection, ClientType type) {
    this.connection = establishConnection(connection, type);
  }

  private Connection establishConnection(String connection, ClientType type){
    try {
      Class.forName(type.getClassPath());

      return DriverManager
          .getConnection(connection);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public ResultSet executeSelect(String sql){
    try {
      System.out.println("executing: " + sql);
      Statement statement = connection.createStatement();
      return statement.executeQuery(sql);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean execute(String sql){
    try {
      System.out.println("executing: " + sql);
      Statement statement = connection.createStatement();
      return statement.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

}
