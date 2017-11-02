package statement;

public class Delete implements FromStatement {

  private String statement = "DELETE FROM ";

  public WhereStatement from(Class table) {
    return new WhereClause(statement + table.getSimpleName());
  }
}
