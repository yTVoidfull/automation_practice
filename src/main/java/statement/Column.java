package statement;

public class Column implements Conditionable {

  private String name;

  public Column(String name) {
    this.name = name;
  }

  public Conditionable equal(Object value) {
    return new Column(String.format("%s = '%s'", name, value.toString()));
  }

  public Conditionable notEqual(Object value) {
    return new Column(String.format("%s != '%s'", name, value.toString()));
  }

  public Conditionable largerThan(Object value) {
    return new Column(String.format("%s > '%s'", name, value.toString()));
  }

  public Conditionable smallerThan(Object value) {
    return new Column(String.format("%s < '%s'", name, value.toString()));
  }

  public Conditionable and(Conditionable condition) {
    return new Column(name + " AND " + condition.toString());
  }

  public Conditionable multipliedByFactor(int factor) {
    return new Column(String.format("%s = %d * %s", name, factor, name));
  }

  public String toString() {
    return name;
  }

}
