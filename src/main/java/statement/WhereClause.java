package statement;

public class WhereClause implements WhereStatement {

  private String statement;

  public WhereClause(String statement){
    this.statement = statement;
  }

  public String end() {
    return statement;
  }

  public WhereClause where(Conditionable condition) {
    return new WhereClause(this.statement + " WHERE " + condition);
  }

}
