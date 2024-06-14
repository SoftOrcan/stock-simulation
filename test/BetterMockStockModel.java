package model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A mock implementation of the {@link IStockBetterModel} interface.
 * This class provides dummy implementations for various stock-related operations.
 */
public class BetterMockStockModel implements IStockBetterModel {
  private final Map<String, BetterPortfolio> portfolios = new HashMap<>();
  private final StockDatabase stockDatabase = new StockDatabase();

  @Override
  public double stockChange(String symbol, LocalDate startDate, LocalDate endDate) {
    return 100.0;
  }
  @Override
  public double stockMovingAverage(String symbol, LocalDate date, int numDays) {
    return 150.0;
  }

  @Override
  public List<LocalDate> stockCrossovers(String symbol, LocalDate startDate, LocalDate endDate, int numDays) {
    return Arrays.asList(startDate, endDate);
  }

  @Override
  public boolean isValidSymbol(String symbol) {
    return true;
  }

  @Override
  public BarGraph stockPerformance(String symbol, LocalDate start, LocalDate end, int xLabelCount) {
    return new BarGraph("Performance");
  }

  @Override
  public void createPortfolio(String name) {
    portfolios.put(name, new BetterPortfolio());
  }

  @Override
  public void addStockToPortfolio(String name, String symbol, double quantity) {
    // Not implemented in the provided BetterPortfolio and StockBetterModel
  }

  @Override
  public void removeStockFromPortfolio(String name, String symbol, int quantity) {
    // Not implemented in the provided BetterPortfolio and StockBetterModel
  }

  @Override
  public void addStockToPortfolio(String name, String symbol, int quantity, LocalDate date) {
    portfolios.get(name).addStockToFile(name, symbol, quantity, date);
  }

  @Override
  public void removeStockFromPortfolio(String name, String symbol, int quantity, LocalDate date) {
    portfolios.get(name).removeStockFromFile(name, symbol, quantity, date);
  }

  @Override
  public double portfolioValue(String name, LocalDate date) {
    return 1000.0;
  }

  @Override
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

  @Override
  public void portfolioRebalance(String name, List<Integer> split, LocalDate date) {
    portfolios.get(name).rebalance(name, split, date, stockDatabase);
  }

  @Override
  public boolean isValidSplit(String name, List<String> split) {
    return portfolios.get(name).isValidSplit(split);
  }
}
