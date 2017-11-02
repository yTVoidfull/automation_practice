package common;

import statement.Insert;
import statement.InsertValues;

import java.io.*;

public class BulkFileInsert {

  private String filePath;
  private int bulkSize;
  private boolean firstRowIsColumnNames;
  private Class table;
  private FileReader fileReader;
  private BufferedReader bufferedReader;
  private String[] columns;

  public BulkFileInsert(String filePath, boolean firstRowIsColumnNames, int bulkSize, Class table) {
    this.filePath = filePath;
    this.bulkSize = bulkSize;
    this.firstRowIsColumnNames = firstRowIsColumnNames;
    this.table = table;
    instantiateReader();
    if(firstRowIsColumnNames){
      setColumnsToFirstlineInfile();
    }
  }

  private void instantiateReader(){
    try {
      fileReader = new FileReader(filePath);
      bufferedReader = new BufferedReader(fileReader);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not instantiate reader");
    }
  }

  private void setColumnsToFirstlineInfile(){
    try {
      columns = bufferedReader.readLine().split(",");
    } catch (IOException e) {
      throw new RuntimeException("Could not read column names");
    }
  }

  public String getBulkInsert(){
    InsertValues insertValues = generateInsertValues();
    if(insertValues.toString().equals("")){
      return null;
    }

    return new Insert()
        .into(table)
        .columns((Object[]) columns)
        .values(insertValues)
        .end();
  }

  private InsertValues generateInsertValues(){
    InsertValues insertValues = new InsertValues();
    for(int i = 0; i < bulkSize; i++){
      try {
        String row = bufferedReader.readLine();
        if(row != null){
          String[] rowValues = row.split(",");
          insertValues.newRow((Object[]) rowValues);
        }
      } catch (IOException e) {
        throw new RuntimeException("Could not read line " + i + " from bulk");
      }
    }
    return insertValues;
  }

  public void finish(){
    try{
      fileReader.close();
      bufferedReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
