package statement;


public class Update {

  private String statement = "UPDATE ";

  public Update(Class table) {
    statement += table.getSimpleName();
  }

  public WhereStatement set(Conditionable s){
   return new WhereClause(statement += " SET " + s);
  }

}
