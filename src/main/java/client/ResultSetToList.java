package client;

import statement.Column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetToList {

  private ResultSet resultSet;
  private Column[] columns;

  public ResultSetToList(ResultSet resultSet, Column... columns) {
    this.resultSet = resultSet;
    this.columns = columns;
  }

  public List<Map<String,String>> invoke(){
    List<Map<String, String>> output = new ArrayList<Map<String, String>>();
    try {
      while (resultSet.next()){
        Map<String, String> row = new HashMap<String, String>();
        for(Column c : columns){
          row.put(c.toString(), resultSet.getString(c.toString()));
        }
        output.add(row);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return output;
  }
}
