import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.IStockModel;

/**
 * The MockStockModel class is a mock for IStockModel.
 */
class MockStockModel implements IStockModel {
  private final List<String> log;

  /**
   * Constructs a MockStockModel instance with an empty log list.
   */
  MockStockModel() {
    log = new ArrayList<>();
  }

  /**
   * Retrieves the log of method calls.
   *
   * @return The list of log entries.
   */
  List<String> getLog() {
    return log;
  }

  /**
   * Mock implementation for calculating stock change.
   *
   * @param symbol    The stock symbol.
   * @param startDate The start date for calculation.
   * @param endDate   The end date for calculation.
   * @return Always returns 0 as a mock value.
   */
  public double stockChange(String symbol, LocalDate startDate, LocalDate endDate) {
    log.add(String.format("calculateChange: %s, %s, %s", symbol, startDate, endDate));
    return 0;
  }

  /**
   * Mock implementation for calculating stock moving average.
   *
   * @param symbol  The stock symbol.
   * @param date    The date for calculation.
   * @param numDays The number of days for the moving average.
   * @return Always returns 0 as a mock value.
   */
  public double stockMovingAverage(String symbol, LocalDate date, int numDays) {
    log.add(String.format("movingAverage: %s, %s, %d", symbol, date, numDays));
    return 0;
  }

  /**
   * Mock implementation for finding stock crossovers.
   *
   * @param symbol    The stock symbol.
   * @param startDate The start date for crossover search.
   * @param endDate   The end date for crossover search.
   * @param x         Parameter description.
   * @return Always returns an empty list as a mock value.
   */
  public List<LocalDate> stockCrossovers(String symbol, LocalDate startDate, LocalDate endDate,
                                        int x) {
    log.add(String.format("findCrossovers: %s, %s, %s, %d", symbol, startDate, endDate, x));
    return new ArrayList<>();
  }

  /**
   * Mock implementation for checking if a symbol is valid.
   *
   * @param symbol The stock symbol to check.
   * @return Always returns true if the symbol is "AAPL".
   */
  public boolean isValidSymbol(String symbol) {
    return symbol.equals("AAPL");
  }

  /**
   * Mock implementation for creating a portfolio.
   *
   * @param portfolioName The name of the portfolio to create.
   */
  public void createPortfolio(String portfolioName) {
    // Mock implementation for createPortfolio.
    log.add(String.format("createPortfolio: %s", portfolioName));
  }

  /**
   * Mock implementation for adding stock to a portfolio.
   *
   * @param portfolioName The name of the portfolio.
   * @param symbol        The stock symbol to add.
   * @param quantity      The quantity of stock to add.
   */
  public void addStockToPortfolio(String portfolioName, String symbol, double quantity) {
    // Mock implementation for addStockToPortfolio.
    log.add(String.format("addStockToPortfolio: %s, %s, %f", portfolioName, symbol, quantity));
  }

  /**
   * Mock implementation for removing stock from a portfolio.
   *
   * @param portfolioName The name of the portfolio.
   * @param symbol        The stock symbol to remove.
   * @param quantity      The quantity of stock to remove.
   */
  public void removeStockFromPortfolio(String portfolioName, String symbol, int quantity) {
    // Mock implementation for removeStockFromPortfolio.
    log.add(String.format("removeStockFromPortfolio: %s, %s, %d", portfolioName, symbol, quantity));
  }

  /**
   * Mock implementation for retrieving the value of a portfolio on a specific date.
   *
   * @param portfolioName The name of the portfolio.
   * @param date          The date for which to retrieve the portfolio value.
   * @return Always returns 0 as a mock value.
   */
  public double portfolioValue(String portfolioName, LocalDate date) {
    // Return a mock value for valueOfPortfolio.
    log.add(String.format("valueOfPortfolio: %s, %s", portfolioName, date));
    return 0;
  }

  /**
   * Mock implementation for checking if a portfolio exists.
   *
   * @param portfolioName The name of the portfolio to check.
   * @return Always returns true if the portfolio name is "MyPortfolio".
   */

  public boolean hasPortfolio(String portfolioName) {
    return portfolioName.equals("MyPortfolio");
  }
}