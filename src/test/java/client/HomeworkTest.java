package client;

import common.PropertiesReader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import statement.*;
import tables.Sales;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static tables.Sales.*;

public class HomeworkTest {

  private Client client;
  private Properties properties = PropertiesReader.getPropertiesFromFile("db.properties");

  @Before
  public void setUp(){
    client = new Client(properties.getProperty("mysql"), ClientType.MY_SQL);
    String clearTable = new Delete().from(Sales.class).end();
    client.execute(clearTable);
  }

  @Test
  public void TestSimpleInsert() throws Exception {
    InsertValues insertValues = new InsertValues()
        .newRow("1", "1", "1", "1", "1", "1", "1", new Date(System.currentTimeMillis()));

    String insertSimple = new Insert().into(Sales.class)
        .columns()
        .values(insertValues)
        .end();
    client.execute(insertSimple);

    String selectSimple = new Select("*").from(Sales.class).end();
    ResultSet resultSet = client.executeSelect(selectSimple);

    resultSet.next();
    assertThat(resultSet.getString(ORDER_ID.toString())).isEqualTo("1");
    assertThat(resultSet.next()).isEqualTo(false);
  }

  @Test
  public void doubleQuantity() throws Exception {
    InsertValues insertValues = new InsertValues()
        .newRow("1", "1", "1", "1", "1", "1", "1", new Date(System.currentTimeMillis()));

    String insertSimple = new Insert().into(Sales.class)
        .columns()
        .values(insertValues)
        .end();
    client.execute(insertSimple);

    String updateDouble = new Update(Sales.class).set(QUANTITY.multipliedByFactor(2)).end();
    client.execute(updateDouble);

    String selectSimple = new Select("*").from(Sales.class).end();
    ResultSet resultSet = client.executeSelect(selectSimple);

    resultSet.next();
    assertThat(resultSet.getString(QUANTITY.toString())).isEqualTo("2");
    assertThat(resultSet.next()).isEqualTo(false);
  }

  @Test
  public void deletePriceLessThan160() throws Exception {
    InsertValues insertValues = new InsertValues()
        .newRow("1", "1", "1", "1", "159", "1", "1", new Date(System.currentTimeMillis()))
        .newRow("2", "2", "2", "2", "161", "1", "1", new Date(System.currentTimeMillis()));

    String insertSimple = new Insert().into(Sales.class)
        .columns()
        .values(insertValues)
        .end();
    client.execute(insertSimple);

    String deleteCondition = new Delete()
        .from(Sales.class)
        .where(UNIT_PRICE.smallerThan("160"))
        .end();
    client.execute(deleteCondition);

    String selectSimple = new Select("*").from(Sales.class).end();
    ResultSet resultSet = client.executeSelect(selectSimple);

    resultSet.next();
    assertThat(resultSet.getString(UNIT_PRICE.toString())).isEqualTo("161.00");
    assertThat(resultSet.next()).isEqualTo(false);
  }

  @Test
  public void extractResultSet() throws Exception {
    InsertValues insertValues = new InsertValues()
        .newRow("1", "1", "1", "1", "159", "1", "1", new Date(System.currentTimeMillis()));

    String insertSimple = new Insert().into(Sales.class)
        .columns()
        .values(insertValues)
        .end();
    client.execute(insertSimple);

    String selectSimple = new Select("*").from(Sales.class).end();
    ResultSet resultSet = client.executeSelect(selectSimple);

    List<Map<String,String>> resultList = new ResultSetToList(resultSet, ORDER_ID, ORDER_DETAIL_ID).invoke();

    assertThat(resultList.size()).isEqualTo(1);
    assertThat(resultList.get(0).get(ORDER_ID.toString())).isEqualTo("1");
  }

  @Test
  @Ignore
  public void loadDataInFile() throws Exception {
    String fileInsert = new LoadDataFromFile("resources/bulk-insert.csv")
        .into(Sales.class)
        .withFieldTermination(",")
        .end();
    client.execute(fileInsert);

    String selectSimple = new Select()
        .from(Sales.class)
        .end();
    ResultSet resultSet = client.executeSelect(selectSimple);
    List<Map<String,String>> resultList = new ResultSetToList(resultSet, ORDER_ID, ORDER_DETAIL_ID).invoke();

    assertThat(resultList.size()).isEqualTo(10);
  }

}
