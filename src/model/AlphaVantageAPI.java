package model;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class AlphaVantageAPI {
  private final Logger logger = Logger.getLogger(AlphaVantageAPI.class.getName());
  private final String apiKey;

  public AlphaVantageAPI(String apiKey) {
    this.apiKey = apiKey;
  }

  public AlphaVantageAPI() {
    this.apiKey = "RJ7QQ6OIQNL5BMJP";
  }

  public void getData(String stockSymbol) {
    URL url;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
      InputStream in = url.openStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      FileWriter fileWriter = new FileWriter(stockSymbol + ".csv");

      String line;
      while ((line = reader.readLine()) != null) {
        fileWriter.write(line + "\n");
      }
      reader.close();
      fileWriter.close();
    }
    catch (MalformedURLException e) {
      logger.severe("Malformed URL: " + e.getMessage());
    } catch (IOException e) {
      logger.severe("IO Exception: " + e.getMessage());
    }
  }
}
