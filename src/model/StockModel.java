package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The StockModel class represents the "backend" of StocksStimulation. Such that, it contains
 * implemented methods to...
 * <li>Calculate the net profit/loss of a stock over a time period (via the 'calculateChange()'
 * method).</li>
 * <li>The moving average of a stock over a time period (via the 'movingAverage ()' method).</li>
 * <li>The crossovers of a stock over a time period (via the 'findCrossovers()' method).</li>
 *
 * <p> Additionally, the StockModel class has capabilities to...</p>
 * <li>Create (via the 'createPortfolio()' method)</li>
 * <li>Modify (via the 'addStockToPortfolio()' and 'removeStockFromPortfolio()' methods)</li>
 * <li>Calculate the value of a portfolio based on the stocks purchased and the price of the
 * stocks on a given date (via the 'valueOfPortfolio()' method).</li>
 */
public class StockModel implements IStockModel {
  // Portfolios owned, represented as a HashMap<PortfolioName (String), Portfolio>.
  private final Map<String, Portfolio> portfolios;
  // The stock database to retrieve information relating to a stock from.
  private final StockDatabase stockDatabase;

  /**
   * Public constructor (utilized by IStockController) that initializes the StockModel.
   */
  public StockModel() {
    // At the initial state, a StockModel contains no portfolios and a stock database to retrieve
    // information from.
    portfolios = new HashMap<>();
    stockDatabase = new StockDatabase();
  }

  /**
   * Calculate the net profit/loss of a stock (termed as 'symbol') from and to a specified date
   * range by referencing stockDatabase. The net profit/loss is:
   * endDate.closingPrice - startDate.closingPrice.
   *
   * @param symbol    The stock to examine.
   * @param startDate The start date to examine.
   * @param endDate   The end date to examine.
   * @return The net profit/loss of a stock over a specified time range.
   */
  public double stockChange(String symbol, LocalDate startDate, LocalDate endDate) {
    return stockDatabase.calculateChange(symbol, startDate, endDate);
  }

  /**
   * Calculate the x-day moving average of a stock over a specified time period by referencing the
   * stockDatabase.
   *
   * @param symbol  The stock to examine.
   * @param date    The start date to examine.
   * @param numDays The last x days to examine.
   * @return The moving average of a stock over a specified time period.
   */
  public double stockMovingAverage(String symbol, LocalDate date, int numDays) {
    return stockDatabase.movingAverage(symbol, date, numDays);
  }

  /**
   * Calculate the x-day crossovers of a stock over a specified time period by referencing the
   * stockDatabase.
   *
   * @param symbol    The stock to examine.
   * @param startDate The start date to examine.
   * @param endDate   The last date to examine.
   * @param numDays   The x-days to examine.
   * @return The x-day crossovers of a stock over a specified time period.
   */
  public List<LocalDate> stockCrossovers(String symbol, LocalDate startDate, LocalDate endDate,
                                         int numDays) {
    return stockDatabase.findCrossovers(symbol, startDate, endDate, numDays);
  }

  /**
   * Determine if a symbol exists.
   *
   * @param symbol The symbol.
   * @return If a symbol exists or not.
   */
  public boolean isValidSymbol(String symbol) {
    return stockDatabase.isValidSymbol(symbol);
  }

  @Override
  public BarGraph stockPerformance(String symbol, LocalDate start, LocalDate end, int xLabelCount) {
    return stockDatabase.performance(symbol, start, end, xLabelCount);
  }

  /**
   * Create a portfolio with a specified name.
   *
   * @param name The name of the portfolio.
   */
  public void createPortfolio(String name) {
  /*  File portfoliosDirectory = new File("./portfolios");
    if (!portfoliosDirectory.exists()) {
      portfoliosDirectory.mkdirs();
    }*/ // TODO - files

    // Add the portfolio to the map
    portfolios.put(name, new Portfolio());
  }

  /**
   * Add a quantity of stock (termed as 'symbol') to a portfolio, representative of purchasing a
   * share of a stock to a portfolio.
   *
   * @param name The name of the portfolio.
   * @param symbol        The stock to add.
   * @param amount        The quantity of the stock to add.
   */
  public void addStockToPortfolio(String name, String symbol, int amount, LocalDate date) {
    portfolios.get(name).add(symbol, amount, date);
  }

  /**
   * Remove a stock (termed as 'symbol') from a portfolio.
   *
   * @param name The name of the portfolio.
   * @param symbol        The stock to remove.
   */
  public void removeStockFromPortfolio(String name, String symbol, int amount, LocalDate date) {
    portfolios.get(name).remove(symbol, amount, date);
  }

  /**
   * Calculate the value of a portfolio on a specific date by referencing the information of the
   * stock in stockDatabase.
   *
   * @param name The name of the portfolio.
   * @param date          The date to examine.
   * @return The value of a portfolio on a specific date.
   */
  public double portfolioValue(String name, LocalDate date) {
    return portfolios.get(name).value(date, stockDatabase);
  }

  /**
   * Determine if the portfolio exists.
   *
   * @param name The name of the portfolio.
   * @return Whether the portfolio exists or not.
   */
  public boolean hasPortfolio(String name) {
    return portfolios.containsKey(name);
  }

  @Override
  public BarGraph portfolioComposition(String name, LocalDate date) {
    return portfolios.get(name).composition(date);
  }

  @Override
  public BarGraph portfolioDistribution(String name, LocalDate date) {
    return portfolios.get(name).distribution(date);
  }

  @Override
  public BarGraph portfolioPerformance(String name, LocalDate start, LocalDate end,
                                       int xLabelCount) {
    return portfolios.get(name).performance(name, start, end, xLabelCount);
  }
}
