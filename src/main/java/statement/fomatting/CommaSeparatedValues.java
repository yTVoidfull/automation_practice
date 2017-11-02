package statement.fomatting;

public class CommaSeparatedValues {

  private String output = "";
  private Object[] values;
  private ValueWrapping wrapping;

  public CommaSeparatedValues(Object[] values, ValueWrapping wrapping) {
    this.values = values;
    this.wrapping = wrapping;
  }

  public String invoke(){
    for (int i = 0; i < values.length; i++) {
      output += wrapping.getBefore() + values[i].toString() + wrapping.getAfter();
      if(!(i == values.length - 1)){
        output +=  ", ";
      }
    }
    return output;
  }


}
