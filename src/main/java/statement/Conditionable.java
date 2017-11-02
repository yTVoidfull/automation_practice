package statement;

public interface Conditionable {

  Conditionable equal(Object value);

  Conditionable notEqual(Object value);

  Conditionable largerThan(Object value);

  Conditionable smallerThan(Object value);

  Conditionable and(Conditionable condition);

  Conditionable multipliedByFactor(int factor);

}
