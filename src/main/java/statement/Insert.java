package statement;

import statement.fomatting.CommaSeparatedValues;
import statement.fomatting.ValueWrapping;

public class Insert implements EndStatement {

  private StringBuffer statement = new StringBuffer("INSERT INTO ");

  public Insert() {
  }

  public Insert into(Class table){
    statement.append(table.getSimpleName());
    return this;
  }

  public Insert columns(Object... columns){
    String values = new CommaSeparatedValues(columns, ValueWrapping.NONE).invoke();
    if (values.length() > 0){
      statement.append("(");
      statement.append(values);
      statement.append(")");
    }

    return this;
  }

  public Insert values(InsertValues values){
    statement.append(" VALUES ");
    statement.append(values.toString());
    return this;
  }

  public String end() {
    return statement.toString();
  }
}
