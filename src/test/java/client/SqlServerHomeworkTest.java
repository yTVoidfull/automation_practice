package client;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCSVFileRecord;
import common.BulkFileInsert;
import common.PropertiesReader;
import org.junit.Before;
import org.junit.Test;
import statement.Delete;
import statement.Insert;
import statement.InsertValues;
import statement.Select;
import tables.Sales;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static tables.Sales.ORDER_ID;

public class SqlServerHomeworkTest {

  private Client client;
  private Properties properties = PropertiesReader.getPropertiesFromFile("db.properties");


  @Before
  public void setUp(){
    client = new Client(properties.getProperty("sqlserver"), ClientType.SQL_SERVER);
    String clearTable = new Delete().from(Sales.class).end();
    client.execute(clearTable);
  }

  @Test
  public void testSqlServerInsert() throws Exception {
    InsertValues insertValues = new InsertValues()
        .newRow("1", "1", "1", "1", "159", "1", "1", new Date(System.currentTimeMillis()));

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
  public void bulkInsertFromFile() throws Exception {
    BulkFileInsert bulkFileInsert = new BulkFileInsert("src/main/resources/bulk-insert.csv",
        true,
        2,
        Sales.class );

    String bulkInsert;
    while ((bulkInsert = bulkFileInsert.getBulkInsert()) != null){
      client.execute(bulkInsert);
    }
    bulkFileInsert.finish();

    String selectAll = new Select().from(Sales.class).end();
    List<Map<String, String>> resultList = new ResultSetToList(client.executeSelect(selectAll)).invoke();
    assertThat(resultList.size()).isEqualTo(10);
  }
}
