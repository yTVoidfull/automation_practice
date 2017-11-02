package common;

import java.io.*;
import java.util.Properties;

public class PropertiesReader {

  public static Properties getPropertiesFromFile(String filePath) {
    try {
      InputStream inputStream = new FileInputStream(filePath);
      Properties prop = new Properties();
      prop.load(inputStream);
      return prop;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
