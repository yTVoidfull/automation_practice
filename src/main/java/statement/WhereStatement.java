package statement;

public interface WhereStatement extends EndStatement {

  WhereStatement where(Conditionable condition);

}
