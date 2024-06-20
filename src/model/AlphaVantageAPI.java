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
  // The apiKey to access AlphaVantageAPI.
  private final String apiKey;

  /**
   * Generated key to access data from the AlphaVantage API.
   */
  AlphaVantageAPI() {
    this.apiKey = "XKQWZTAHCRNG4LG4";
  }

  /**
   * Retrieve historical data of a stock from the Alpha Vantage API and store the information
   * locally as a .csv file.
   *
   * @param symbol The stock to examine.
   * @param interval The time interval to examine (i.e. either DAILY or HOURLY stock).
   */
  void getData(String symbol, String interval) {
    try {
      BufferedReader reader = getBufferedReader(symbol, interval); // Read data from the URL.
      // PrintWriter to append to the .csv file.
      PrintWriter writer = new PrintWriter("./data/" + interval + symbol + ".csv");

      String line;
      // For every stock data in the URL, copy the information to the .csv.
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

  /**
   * Create the URL. This is the query to the web service. The query string includes the type of
   * query (i.e. DAILY or INTRADAY stock prices) (termed as 'interval') and the stock symbol to be
   * looked up (termed as 'symbol').
   *
   * @param symbol   The stock to examine.
   * @param interval The interval to examine (i.e. "" or "60min").
   * @return BufferedReader to read the data contained in the URL.
   * @throws IOException If the URL is deteriorated or cannot be accessed.
   */
  private BufferedReader getBufferedReader(String symbol, String interval) throws IOException {
    String baseURL = "https://www.alphavantage.co/query?function=";
    // An empty 'interval' represents a DAILY stock retrieval, and a "60min" 'interval'
    // represents an HOURLY stock retrieval.
    String apiURL = interval.isEmpty() ?
        String.format("%sTIME_SERIES_DAILY&symbol=%s&outputsize=full&apikey=%s&datatype" +
            "=csv", baseURL, symbol, apiKey) :
        String.format("%sTIME_SERIES_INTRADAY" +
            "&symbol=%s&interval=%s&apikey=%s&datatype=csv", baseURL, symbol, interval, apiKey);


    URL url = new URL(apiURL);
    return new BufferedReader(new InputStreamReader(url.openStream())); // Read data from the URL.
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
