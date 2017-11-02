package statement.fomatting;

public enum ValueWrapping {

  NONE("", ""),
  QUOTES("'", "'"),
  ROUND_BRACES("(", ")");

  private String before;
  private String after;

  ValueWrapping(String before, String after) {
    this.before = before;
    this.after = after;
  }

  public String getBefore() {
    return before;
  }

  public String getAfter() {
    return after;
  }
}
