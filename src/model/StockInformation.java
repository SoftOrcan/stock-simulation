package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The StockInformation class performs calculations relating to a stock's...
 * <li>Net profit/loss over a time range (via the 'calculateChange()' method).</li>
 * <li>The moving average over a previous time period (via the 'movingAverage()' method).</li>
 * <li>The crossovers overtime (via the method 'findCrossovers()').</li>
 * <li>The price of a Stock on a specified date (via the method 'getPriceOn()')</li>
 *
 * <p>In addition, the StockInformation class contains a private method that retrieves the
 * opening and closing prices of a Stock (via the method 'getHistoricalPrices()').</p>
 */
class StockInformation {
  // Logger to record functionality or capture thrown exceptions.
  private final Logger logger = Logger.getLogger(StockInformation.class.getName());
  // HashMap that correlates a stock's opening and closing prices to a date.
  private final Map<LocalDate, StockPrice> prices;

  /**
   * Package-private constructor utilized to retrieve the historical data of a stock (termed as
   * 'symbol').
   *
   * @param symbol The stock to examine.
   */
  StockInformation(String symbol) {
    this.prices = new HashMap<>();
    // Retrieve the historical data of a stock.
    this.getHistoricalPrices(symbol);
  }

  /**
   * Retrieve a stock's (termed as 'symbol') historical data from the .csv file.
   *
   * @param symbol The stock to examine.
   */
  private void getHistoricalPrices(String symbol) {
    try (BufferedReader br = // Attempt to read the .csv file, if it exists.
                 new BufferedReader(new FileReader("./data/" + symbol + ".csv"))) {
      String line;
      br.readLine(); // Skip header.
      // While there are entries...
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        LocalDate date = LocalDate.parse(values[0]); // Retrieve the data of the stock.
        double open = Double.parseDouble(values[1]); // Retrieve the opening price of the stock.
        double close = Double.parseDouble(values[4]); // Retrieve the closing price of the stock.
        prices.put(date, new StockPrice(open, close)); // Store the data in the Prices field.
      }
    } catch (IOException e) {
      logger.severe("Error reading file: " + e.getMessage());
    }
  }

  /**
   * Calculate the net profit/loss of a stock (termed as 'symbol') from and to a specified date
   * range.
   *
   * @param startDate The start date to examine.
   * @param endDate   The end date to examine.
   * @return The net profit/loss of a stock over a specified time range.
   */
  double calculateChange(LocalDate startDate, LocalDate endDate) {
    return getPriceOn(endDate) - getPriceOn(startDate);
  }

  /**
   * Calculate the x-day moving average of a stock over a specified time period. The x-day moving
   * average is the average of the last x days (starting from the given date). Note: This
   * corresponds to the last x days when the stock prices are available.
   *
   * @param date    The start date to examine.
   * @param numDays The x-days to examine.
   * @return The moving average of a stock over a specified time period.
   */
  double movingAverage(LocalDate date, int numDays) {
    double total = 0;
    for (int i = 0; i < numDays; i++) {
      // De-advance in time, and retrieve the price of the stock on previous days.
      total += getPriceOn(date.minusDays(i));
    }
    return total / numDays; // Calculate the average.
  }

  /**
   * Calculate the x-day crossovers of a stock over a specified time period. An x-day crossover
   * happens when the closing price for a day is greater than the x-day moving average for that
   * day. A crossover signals a "buy" opportunity. A crossover can be defined over any period of
   * time, although the 30-day period (i.e. the 30-day crossover) is usually popular.
   *
   * @param startDate The start date to examine.
   * @param endDate   The last date to examine.
   * @param numDays   The x-days to examine.
   * @return The x-day crossovers of a stock over a specified time period.
   */
  List<LocalDate> findCrossovers(LocalDate startDate, LocalDate endDate, int numDays) {
    LocalDate date = startDate;
    List<LocalDate> crossovers = new ArrayList<>();
    // While the end date has not been reached...
    while (!date.isEqual(endDate)) {
      // If the stock's price on the date is greater than the moving average...
      if (getPriceOn(date) > movingAverage(date, numDays)) {
        // ... add it to 'crossovers' array.
        crossovers.add(date);
      }
      // Increment the date.
      date = date.plusDays(1);
    }
    return crossovers;
  }

  /**
   * Return the price of a stock on a specified date.
   *
   * @param date The specified date to examine.
   * @return The price of a stock on a specified date.
   */
  double getPriceOn(LocalDate date) {
    StockPrice price;
    // While there are no entries for the date...
    while ((price = prices.get(date)) == null) {
      // De-advance in time.
      date = date.minusDays(1);
    }
    // Return the closing price of the stock for the found date.
    return price.closePrice;
  }
}