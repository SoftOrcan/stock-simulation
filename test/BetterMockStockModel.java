package model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A mock implementation of the {@link IStockBetterModel} interface. This class provides dummy
 * implementations for various stock-related operations.
 */
public class BetterMockStockModel implements IStockBetterModel {
  private final Map<String, BetterPortfolio> portfolios = new HashMap<>();
  private final StockDatabase stockDatabase = new StockDatabase();

  /**
   * Mock implementation of the 'stockChange()' method in IStockBetterModel.
   *
   * @param symbol    The stock to examine.
   * @param startDate The start date to examine.
   * @param endDate   The end date to examine.
   * @return 100.0, as this is a mock implementation.
   */
  @Override
  public double stockChange(String symbol, LocalDate startDate, LocalDate endDate) {
    return 100.0;
  }

  /**
   * Mock implementation of the 'stockMovingAverage()' method in IStockBetterModel.
   *
   * @param symbol  The stock to examine.
   * @param date    The start date to examine.
   * @param numDays The last x days to examine.
   * @return 150.0, as this is a mock implementation.
   */
  @Override
  public double stockMovingAverage(String symbol, LocalDate date, int numDays) {
    return 150.0;
  }

  /**
   * Mock implementation of the 'stockCrossovers()' method in IStockBetterModel.
   *
   * @param symbol    The stock to examine.
   * @param startDate The start date to examine.
   * @param endDate   The last date to examine.
   * @param numDays   The x-days to examine.
   * @return The dates of the crossovers, as this is a mock implementation.
   */
  @Override
  public List<LocalDate> stockCrossovers(String symbol, LocalDate startDate, LocalDate endDate,
                                         int numDays) {
    return Arrays.asList(startDate, endDate);
  }

  /**
   * Mock implementation of the 'isValidSymbol()' method in IStockBetterModel.
   *
   * @param symbol The symbol.
   * @return True, as this is a mock implementation.
   */
  @Override
  public boolean isValidSymbol(String symbol) {
    return true;
  }

  /**
   * Mock implementation of the 'stockPerformance()' method in IStockBetterModel.
   *
   * @param symbol      The stock symbol to examine.
   * @param start       The start date to examine.
   * @param end         The end date to examine.
   * @param xLabelCount The quantity of x-intervals to construct.
   * @return A mock BarGraph, as this is a mock implementation.
   */
  @Override
  public BarGraph stockPerformance(String symbol, LocalDate start, LocalDate end, int xLabelCount) {
    return new BarGraph("Performance");
  }

  /**
   * Mock implementation of the 'createPortfolio()' method in IStockBetterModel.
   *
   * @param name The name of the portfolio.
   */
  @Override
  public void createPortfolio(String name) {
    portfolios.put(name, new BetterPortfolio());
  }

  /**
   * Mock implementation of the 'addStockToPortfolio()' method in IStockBetterModel.
   *
   * @param name     The name of the portfolio.
   * @param symbol   The stock to add.
   * @param quantity The quantity of the stock to add.
   */
  @Override
  public void addStockToPortfolio(String name, String symbol, double quantity) {
    // Not implemented in the provided BetterPortfolio and StockBetterModel.
  }

  /**
   * Mock implementation of the 'removeStockFromPortfolio()' method in IStockBetterModel.
   *
   * @param name     The name of the portfolio.
   * @param symbol   The stock to remove.
   * @param quantity The quantity of stock to remove.
   */
  @Override
  public void removeStockFromPortfolio(String name, String symbol, int quantity) {
    // Not implemented in the provided BetterPortfolio and StockBetterModel.
  }

  /**
   * Mock implementation of the 'addStockToPortfolio()' method in IStockBetterModel.
   *
   * @param name     The name of the portfolio.
   * @param symbol   The stock to add.
   * @param quantity The quantity of stocks purchased.
   * @param date     The date of the purchase of the stock.
   */
  @Override
  public void addStockToPortfolio(String name, String symbol, int quantity, LocalDate date) {
    portfolios.get(name).addStockToFile(name, symbol, quantity, date);
  }

  /**
   * Mock implementation of the 'removeStockFromPortfolio()' method in IStockBetterModel.
   *
   * @param name     The name of the portfolio.
   * @param symbol   The stock to remove.
   * @param quantity The quantity of stock to remove.
   * @param date     The date of the removal of the stock.
   */
  @Override
  public void removeStockFromPortfolio(String name, String symbol, int quantity, LocalDate date) {
    portfolios.get(name).removeStockFromFile(name, symbol, quantity, date);
  }

  /**
   * Mock implementation of the 'portfolioValue()' method in IStockBetterModel.
   *
   * @param name The name of a portfolio.
   * @param date The specified date to retrieve data regarding a stock on.
   * @return 100.0, as this is a mock implementation.
   */
  @Override
  public double portfolioValue(String name, LocalDate date) {
    return 1000.0;
  }

  /**
   * Mock implementation of the 'hasPortfolio()' method in IStockBetterModel.
   *
   * @param name The name of the portfolio.
   * @return Determine if the portfolio name exists in the HashMap.
   */
  @Override
  public boolean hasPortfolio(String name) {
    return portfolios.containsKey(name);
  }

  /**
   * Mock implementation of the 'portfolioComposition()' method in IStockBetterModel.
   *
   * @param name The name of a portfolio.
   * @param date The date to examine.
   * @return The composition of the portfolio on a specified date.
   */
  @Override
  public BarGraph portfolioComposition(String name, LocalDate date) {
    return portfolios.get(name).composition(date);
  }

  /**
   * Mock implementation of the 'portfolioDistribution()' method in IStockBetterModel.
   *
   * @param name The name of a portfolio.
   * @param date The date to examine.
   * @return The distribution of the portfolio on a specified date.
   */
  @Override
  public BarGraph portfolioDistribution(String name, LocalDate date) {
    return portfolios.get(name).distribution(date);
  }

  /**
   * Mock implementation of the 'portfolioPerformance()' method in IStockBetterModel.
   *
   * @param name        The name of the portfolio.
   * @param start       The start date to examine.
   * @param end         The end date to examine.
   * @param xLabelCount The quantity of x-axis labels.
   * @return The performance of the portfolio over a specified time period.
   */
  @Override
  public BarGraph portfolioPerformance(String name, LocalDate start, LocalDate end,
                                       int xLabelCount) {
    return portfolios.get(name).performance(name, start, end, xLabelCount);
  }

  /**
   * Mock implementation of the 'portfolioRebalance()' method in IStockBetterModel.
   *
   * @param name  The name of the portfolio.
   * @param split The weight of each stock.
   * @param date  The date to examine.
   */
  @Override
  public void portfolioRebalance(String name, List<Integer> split, LocalDate date) {
    portfolios.get(name).rebalance(name, split, date, stockDatabase);
  }

  /**
   * Mock implementation of the 'isValidSplit()' method in IStockBetterModel.
   *
   * @param name  The name of the portfolio.
   * @param split The weight of each stock in the portfolio.
   * @return Determine if the split provides a numerical weight to every stock in the portfolio.
   */
  @Override
  public boolean isValidSplit(String name, List<String> split) {
    return portfolios.get(name).isValidSplit(split);
  }
}
