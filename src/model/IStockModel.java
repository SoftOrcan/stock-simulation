package model;

import java.time.LocalDate;
import java.util.List;

/**
 * The IStockModel interface represents the "backend" of StocksStimulation. Such that, it
 * contains methods to...
 * <li>Calculate the net profit/loss of a stock over a time period (via the 'calculateChange()'
 * method).</li>
 * <li>The moving average of a stock over a time period (via the 'movingAverage ()' method).</li>
 * <li>The crossovers of a stock over a time period (via the 'findCrossovers()' method).</li>
 *
 * <p> Additionally, the IStockModel interface has capabilities to...</p>
 * <li>Create (via the 'createPortfolio()' method)</li>
 * <li>Modify (via the 'addStockToPortfolio()' and 'removeStockFromPortfolio()' methods)</li>
 * <li>Calculate the value of a portfolio based on the stocks purchased and the price of the
 * stocks on a given date (via the 'valueOfPortfolio()' method).</li>
 */
public interface IStockModel {
  /**
   * Calculate the net profit/loss of a stock (termed as 'symbol') from and to a specified date
   * range.
   *
   * @param symbol    The stock to examine.
   * @param startDate The start date to examine.
   * @param endDate   The end date to examine.
   * @return The net profit/loss of a stock over a specified time range.
   */
  double calculateChange(String symbol, LocalDate startDate, LocalDate endDate);

  /**
   * Calculate the x-day moving average of a stock over a specified time period. The x-day moving
   * average is the average of the last x days (starting from the given date). Note: This
   * corresponds to the last x days when the stock prices are available.
   *
   * @param symbol  The stock to examine.
   * @param date    The start date to examine.
   * @param numDays The last x days to examine.
   * @return The moving average of a stock over a specified time period.
   */
  double movingAverage(String symbol, LocalDate date, int numDays);

  /**
   * Calculate the x-day crossovers of a stock over a specified time period. An x-day crossover
   * happens when the closing price for a day is greater than the x-day moving average for that
   * day. A crossover signals a "buy" opportunity. A crossover can be defined over any period of
   * time, although the 30-day period (i.e. the 30-day crossover) is usually popular.
   *
   * @param symbol    The stock to examine.
   * @param startDate The start date to examine.
   * @param endDate   The last date to examine.
   * @param numDays   The x-days to examine.
   * @return The x-day crossovers of a stock over a specified time period.
   */
  List<LocalDate> findCrossovers(String symbol, LocalDate startDate, LocalDate endDate,
                                 int numDays);

  /**
   * Create a portfolio with a specified name. A portfolio is utilized to contain shares of one or
   * more stock.
   *
   * @param portfolioName The name of the portfolio.
   */
  void createPortfolio(String portfolioName);

  /**
   * Add a stock (termed as 'symbol') to a portfolio, representative of purchasing a share of a
   * stock to a portfolio.
   *
   * @param portfolioName The name of the portfolio.
   * @param symbol        The stock to add.
   * @param amount        The quantity of the stock to add.
   */
  void addStockToPortfolio(String portfolioName, String symbol, int amount);

  /**
   * Remove a stock (termed as 'symbol') from a portfolio.
   *
   * @param portfolioName The name of the portfolio.
   * @param symbol        The stock to remove.
   */
  void removeStockFromPortfolio(String portfolioName, String symbol);

  /**
   * Calculate the value of a portfolio on a specific date. Calculate by identifying the value of
   * each stock in the portfolio on the specified date multiplied by the quantity of the respective
   * stock.
   *
   * @param portfolioName The name of the portfolio.
   * @param date          The date to examine.
   * @return The value of a portfolio on a specific date.
   */
  double valueOfPortfolio(String portfolioName, LocalDate date);
}