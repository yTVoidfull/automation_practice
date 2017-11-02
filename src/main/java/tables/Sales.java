package tables;

import statement.Column;

public class Sales {

  public static final Column ORDER_ID = new Column("SalesOrderID");
  public static final Column ORDER_DETAIL_ID = new Column("SalesOrderDetailID");
  public static final Column QUANTITY = new Column("OrderQty");
  public static final Column PRODUCT_ID = new Column("ProductID");
  public static final Column UNIT_PRICE = new Column("UnitPrice");
  public static final Column UNIT_PRICE_DISCOUNT = new Column("UnitPriceDiscount");
  public static final Column ROWGUID = new Column("rowguid");
  public static final Column MODIFIED_DATE = new Column("ModifiedDate");

}
