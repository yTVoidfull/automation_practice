package statement;

import org.junit.Test;
import tables.Sales;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static tables.Sales.ORDER_DETAIL_ID;
import static tables.Sales.ORDER_ID;

public class StatementTest {

  @Test
  public void testEmptyStatement() throws Exception {
    String s = new Select("*").from(Sales.class).where(new Column("condition")).end();
    assertThat(s).isEqualTo("SELECT * FROM Sales WHERE condition");
  }


  @Test
  public void testSelectColumns() throws Exception {
    String select = new Select(ORDER_ID, ORDER_DETAIL_ID).from(Sales.class).where(new Column("condition")).end();
    assertThat(select).isEqualTo("SELECT SalesOrderID, SalesOrderDetailID FROM Sales WHERE condition");
  }

  @Test
  public void testWhereClause() throws Exception {
    String select = new Select(ORDER_ID, ORDER_DETAIL_ID)
        .from(Sales.class)
        .where(ORDER_ID.equal("12345"))
        .end();
    assertThat(select).isEqualTo("SELECT SalesOrderID, SalesOrderDetailID FROM Sales WHERE SalesOrderID = '12345'");
  }

  @Test
  public void testUpdate() throws Exception {
    String update = new Update(Sales.class)
        .set(ORDER_ID.equal("11111").and(ORDER_DETAIL_ID.equal("123123")))
        .where(ORDER_ID.equal("12345").and(ORDER_DETAIL_ID.notEqual("abcabc")))
        .end();

    assertThat(update).isEqualTo("UPDATE Sales SET SalesOrderID = '11111' AND SalesOrderDetailID = '123123' WHERE SalesOrderID = '12345' " +
        "AND SalesOrderDetailID != 'abcabc'");
  }

  @Test
  public void testInsertValues() throws Exception {
    InsertValues insertValues = new InsertValues().newRow("0", "0", "0", "0", "0", "0", "0", "0")
        .newRow("1", "1", "1", "1", "1", "1", "1", "1");
    String insertStatement = new Insert().into(Sales.class).columns().values(insertValues).end();
    assertThat(insertStatement).isEqualTo("INSERT INTO Sales() VALUES ('0', '0', '0', '0', '0', '0', '0', '0'), ('1', '1', '1', '1', '1', '1', '1', '1')");
  }
}
