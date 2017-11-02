package client;

public enum ClientType {

  MY_SQL("com.mysql.jdbc.Driver"),
  SQL_SERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver");

  private String classPath;

  ClientType(String classPath) {
    this.classPath = classPath;
  }

  public String getClassPath() {
    return classPath;
  }
}
