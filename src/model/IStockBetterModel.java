package model;

import java.time.LocalDate;
import java.util.List;

/**
 * The IStockBetterModel interface represents the "backend" of StocksStimulation. Such that, it
 * contains methods to...
 * <li>Create (via the 'createPortfolio()' method)</li>
 * <li>Modify (via the 'addStockToPortfolio()' and 'removeStockFromPortfolio()' methods)</li>
 * <li>Calculate the value of a portfolio based on the stocks purchased and the price of the
 * stocks on a given date (via the 'portfolioValue()' method).</li>
 * <li>Display the quantity of each stock in the portfolio (via the 'portfolioComposition()'
 * method)</li>
 * <li>Display the value of each stock in the portfolio (via the 'portfolioDistribution()'
 * method)</li>
 * <li>Rebalance the stocks in a portfolio based on the given weights (via the 'portfolioRebalance
 * ()' method) .</li>
 */
public interface IStockBetterModel extends IStockModel {
  /**
   * Create a portfolio with a specified name. A portfolio is utilized to contain shares of one or
   * more stock.
   *
   * @param name The name of the portfolio.
   */
  void createPortfolio(String name);

  /**
   * Package-private. Add a stock (termed as 'symbol') and the quantity of the stock to a Portfolio
   * on a specified date. Furthermore, reflect the changes to the .csv representation of the
   * portfolio.
   *
   * @param name     The name of the portfolio.
   * @param symbol   The stock to add.
   * @param quantity The quantity of stocks purchased.
   * @param date     The date of the purchase of the stock.
   */
  void addStockToPortfolio(String name, String symbol, int quantity, LocalDate date);

  /**
   * Package-private. Remove a quantity of shares of a stock (termed as 'symbol') from a Portfolio.
   *
   * @param name     The name of the portfolio.
   * @param symbol   The stock to remove.
   * @param quantity The quantity of stock to remove.
   * @param date     The date of the removal of the stock.
   */
  void removeStockFromPortfolio(String name, String symbol, int quantity, LocalDate date);

  /**
   * Package-private. Calculate the value of a portfolio based on the stocks' value on a given date
   * (termed as 'date') and the quantity of each stock.
   *
   * @param name The name of a portfolio.
   * @param date The specified date to retrieve data regarding a stock on.
   * @return The cumulative value of a BetterPortfolio.
   */
  double portfolioValue(String name, LocalDate date);

  /**
   * Visualize the composition of a portfolio (i.e. the quantity of each stock in the portfolio) on
   * a given date.
   *
   * @param name The name of a portfolio.
   * @param date The date to examine.
   * @return A textual visualization of the quantity of each stock in the portfolio.
   */
  BarGraph portfolioComposition(String name, LocalDate date);

  /**
   * Visualize the distribution of a portfolio (i.e. the value of each stock in the portfolio) on a
   * given date.
   *
   * @param name The name of a portfolio.
   * @param date The date to examine.
   * @return A textual visualization of the value of each stock in the portfolio.
   */
  BarGraph portfolioDistribution(String name, LocalDate date);

  /**
   * Visualize the performance of a stock over a time period. The quantity of x-intervals is based
   * on the parameter 'xLabelCount'.
   *
   * @param symbol      The stock symbol to examine.
   * @param start       The start date to examine.
   * @param end         The end date to examine.
   * @param xLabelCount The quantity of x-intervals to construct.
   * @return A textual visualization of a stock over a time period.
   */
  BarGraph stockPerformance(String symbol, LocalDate start, LocalDate end, int xLabelCount);

  /**
   * Visualize the performance of a portfolio over a time period. The quantity of x-axis labels is
   * based on the parameter 'xLabelCount'.
   *
   * @param name        The name of the portfolio.
   * @param start       The start date to examine.
   * @param end         The end date to examine.
   * @param xLabelCount The quantity of x-axis labels.
   * @return Textual visualization of the portfolio over a time period.
   */
  BarGraph portfolioPerformance(String name, LocalDate start, LocalDate end, int xLabelCount);

  /**
   * Rebalance a portfolio to ensure that the weight of each stock is equivalent. The weight is
   * based on the parameter 'split'.
   *
   * @param name  The name of the portfolio.
   * @param split The weight of each stock.
   * @param date  The date to examine.
   */
  void portfolioRebalance(String name, List<Integer> split, LocalDate date);

  /**
   * Determine if a portfolio exists.
   *
   * @param name The name of the portfolio.
   * @return If a portfolio exists or not.
   */
  boolean hasPortfolio(String name);

  /**
   * Validate the user input, ensuring that every stock in the portfolio contains a weight and each
   * value is numerical.
   *
   * @param split The weight of each stock in the portfolio.
   * @return If the user input contains numerical values, and provides a weight to every stock.
   */
  boolean isValidSplit(String name, List<String> split);
}
