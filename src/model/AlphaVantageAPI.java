package model;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * The AlphaVantageAPI class retrieves data from the AlphaVantage API by directly accessing the data
 * from the URL (varies depending on the stock, or 'symbol', name), and saves the stock's historical
 * data locally as a .csv file (via the 'getData()' method).
 */
class AlphaVantageAPI {
  // Logger to record functionality or capture thrown exceptions.
  private final Logger logger = Logger.getLogger(AlphaVantageAPI.class.getName());
  private final String apiKey; // The apiKey to access AlphaVantageAPI.

  /**
   * Generated key to access data from the AlphaVantage API.
   */
  AlphaVantageAPI() {
    this.apiKey = "XKQWZTAHCRNG4LG4";
  }

  /**
   * Retrieve historical data of a stock and store the information locally as a .csv file.
   *
   * @param symbol The stock to examine.
   */
  void getData(String symbol, String interval) {
    try {
      BufferedReader reader = getBufferedReader(symbol, interval);
      PrintWriter writer = new PrintWriter("./data/" + interval + symbol + ".csv");

      String line;
      while ((line = reader.readLine()) != null) {
        writer.write(line + "\n");
      }
      reader.close();
      writer.close();
    } catch (MalformedURLException e) {
      logger.severe("Malformed URL: " + e.getMessage());
    } catch (IOException e) {
      logger.severe("IO Exception: " + e.getMessage());
    }
  }

  private BufferedReader getBufferedReader(String symbol, String interval) throws IOException {
    String baseURL = "https://www.alphavantage.co/query?function=";
    String apiURL = interval.isEmpty() ?
        String.format("%sTIME_SERIES_DAILY&symbol=%s&outputsize=full&apikey=%s&datatype" +
            "=csv", baseURL, symbol, apiKey) :
        String.format("%sTIME_SERIES_INTRADAY" +
            "&symbol=%s&interval=%s&apikey=%s&datatype=csv", baseURL, symbol, interval, apiKey);


    URL url = new URL(apiURL);
    return new BufferedReader(new InputStreamReader(url.openStream()));
  }

  /**
   * Determine if a stock symbol exists.
   *
   * @param symbol The stock to validate.
   * @return If a stock symbol exists or not.
   */
  public boolean isValidSymbol(String symbol) {
    URL url;
    try {
      // Generate a URL to search for the symbol.
      url = new URL("https://www.alphavantage.co/query?function=SYMBOL_SEARCH"
          + "&keywords=" + symbol + "&apikey=" + apiKey + "&datatype=csv");
      InputStream in = url.openStream(); // Open an input stream to read data from the URL.
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));

      String line;
      reader.readLine(); // Skip header.
      // While there are entries...
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(",");
        if (values[0].equals(symbol)) {
          // If the symbol exists, return true.
          return true;
        }
      }
    } catch (MalformedURLException e) {
      // Log a severe message with the exception message.
      logger.severe("Malformed URL: " + e.getMessage());
    } catch (IOException e) {
      // Log a severe message with the exception message.
      logger.severe("IO Exception: " + e.getMessage());
    }
    return false;
  }
}
