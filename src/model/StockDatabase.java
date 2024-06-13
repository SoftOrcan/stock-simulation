package model;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * The StockDatabase class retrieves stock information directly from the locally-stored .csv file
 * (via the 'getStockInformation()' method), to determine...
 * <li>Net profit/loss over a time range (via the 'calculateChange()' method).</li>
 * <li>The moving average over a previous time period (via the 'movingAverage()' method).</li>
 * <li>The crossovers overtime (via the 'findCrossovers()' method).</li>
 * <li>The price of a Stock on a specified date (via the 'getPriceOn()' method)</li>
 * <li>Determine if a stock symbol exists (via the 'isValidSymbol()' method)</li>
 */
class StockDatabase {
  // The AlphaVantage API to retrieve stock data from.
  private final AlphaVantageAPI api = new AlphaVantageAPI();

  /**
   * Retrieve information regarding a stock from either the AlphaVantage API or from the
   * locally-stored .csv files.
   *
   * @param symbol The stock to retrieve.
   * @return A StockInformation containing the opening and closing prices of the stock.
   */
  private StockInformation getStockInformation(String symbol, String interval) {
    // Check if file exists.
    for (File f : Objects.requireNonNull(new File("./data").listFiles())) {
      if (f.getName().equals(interval + symbol + ".csv")) {
        // File exists -> return the stock information.
        return new StockInformation(symbol, interval);
      }
    }
    // Retrieve the stock from the API.
    api.getData(symbol, interval);
    // File exists -> return the stock information.
    return new StockInformation(symbol, interval);
  }

  /**
   * Calculate the net profit/loss of a stock (termed as 'symbol') from and to a specified date
   * range.
   *
   * @param symbol    The stock to examine.
   * @param startDate The start date to examine.
   * @param endDate   The end date to examine.
   * @return The net profit/loss of a stock over a specified time range.
   */
  double calculateChange(String symbol, LocalDate startDate, LocalDate endDate) {
    return getStockInformation(symbol, "").calculateChange(startDate, endDate);
  }

  /**
   * Calculate the x-day moving average of a stock over a specified time period. The x-day moving
   * average is the average of the last x days (starting from the given date). Note: This
   * corresponds to the last x days when the stock prices are available.
   *
   * @param symbol  The stock to examine.
   * @param date    The start date to examine.
   * @param numDays The x-days to examine.
   * @return The moving average of a stock over a specified time period.
   */
  double movingAverage(String symbol, LocalDate date, int numDays) {
    return getStockInformation(symbol, "").movingAverage(date, numDays);
  }

  /**
   * Calculate the x-day crossovers of a stock over a specified time period. An x-day crossover
   * happens when the closing price for a day is greater than the x-day moving average for that day.
   * A crossover signals a "buy" opportunity. A crossover can be defined over any period of time,
   * although the 30-day period (i.e. the 30-day crossover) is usually popular.
   *
   * @param symbol    The stock to examine.
   * @param startDate The start date to examine.
   * @param endDate   The last date to examine.
   * @param numDays   The x-days to examine.
   * @return The x-day crossovers of a stock over a specified time period.
   */
  List<LocalDate> findCrossovers(String symbol, LocalDate startDate, LocalDate endDate,
                                 int numDays) {
    return getStockInformation(symbol, "").findCrossovers(startDate, endDate, numDays);
  }

  /**
   * Return the price of a stock on a specified date.
   *
   * @param symbol   The stock to examine.
   * @param date     The specified date to examine.
   * @param interval The interval to examine.
   * @return The price of a stock on a specified date.
   */
  double getPriceOn(String symbol, String date, String interval) {
    return getStockInformation(symbol, interval).getPriceOn(date);
  }

  /**
   * Determine if a symbol exists.
   *
   * @param symbol The symbol.
   * @return If a symbol exists or not.
   */
  boolean isValidSymbol(String symbol) {
    return api.isValidSymbol(symbol);
  }

  BarGraph performance(String symbol, LocalDate start, LocalDate end, int xLabelCount) {
    BarGraph graph = new BarGraph("Performance of '" + symbol + "':");
    graph.createXAxis(start, end, xLabelCount);
    graph.createStockYAxis(symbol, start, end);
    return graph;
  }
}
