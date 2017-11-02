package statement;

import statement.fomatting.CommaSeparatedValues;
import statement.fomatting.ValueWrapping;

public class Select implements FromStatement {

  private String statement = "SELECT *";

  public Select(Object...columns) {
    addColumnsToStatement(columns);
  }

  public WhereStatement from(Class table) {
    return new WhereClause(statement + " FROM " +table.getSimpleName());
  }

  private void addColumnsToStatement(Object...columns){
    String commaSeparatedColumns = new CommaSeparatedValues(columns, ValueWrapping.NONE).invoke();
    if(commaSeparatedColumns.length() > 0){
      statement = statement.replace("*", commaSeparatedColumns);
    }
  }
}
