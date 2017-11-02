package statement;

public class LoadDataFromFile implements EndStatement {

  private String statement = "LOAD DATA INFILE '";

  public LoadDataFromFile(String filePath) {
    statement += filePath +"'";
  }

  public LoadDataFromFile into(Class table){
    statement += " INTO TABLE " + table.getSimpleName();
    return this;
  }

  public LoadDataFromFile withFieldTermination(String fieldTermination){
    statement += " FIELDS TERMINATED BY '" + fieldTermination + "'";
    return this;
  }

  public String end() {
    return statement;
  }
}
