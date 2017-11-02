package statement;

import statement.fomatting.CommaSeparatedValues;
import statement.fomatting.ValueWrapping;

public class InsertValues {

  private StringBuffer statement = new StringBuffer("");

  public InsertValues newRow(Object... values){
    if(statement.lastIndexOf(")") == statement.length()-1 && statement.length() > 0){
      statement.append(", ");
    }
    statement.append("(");
    statement.append(new CommaSeparatedValues(values, ValueWrapping.QUOTES).invoke());
    statement.append(")");
    return this;
  }

  @Override
  public String toString() {
    return statement.toString();
  }
}
