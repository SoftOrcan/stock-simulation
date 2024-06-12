package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockBetterModel extends StockModel implements IStockBetterModel {
  // Portfolios owned, represented as a HashMap<PortfolioName (String), Portfolio>.
  private final Map<String, Portfolio> portfolios;
  // The stock database to retrieve information relating to a stock from.
  private final StockDatabase stockDatabase;

  public StockBetterModel() {
    // At the initial state, a StockModel contains no portfolios and a stock database to retrieve
    // information from.
    portfolios = new HashMap<>();
    stockDatabase = new StockDatabase();
  }

  public void addStockToPortfolio(String name, String symbol, int amount, LocalDate date) {
    portfolios.get(name).addOnDate(symbol, amount, date);
  }

  public void removeStockFromPortfolio(String name, String symbol, int amount, LocalDate date) {
    portfolios.get(name).removeOnDate(symbol, amount, date);
  }

  public BarGraph stockPerformance(String symbol, LocalDate start, LocalDate end, int xLabelCount) {
    return stockDatabase.performance(symbol, start, end, xLabelCount);
  }

  public BarGraph portfolioComposition(String name, LocalDate date) {
    return portfolios.get(name).composition(date);
  }

  public BarGraph portfolioDistribution(String name, LocalDate date) {
    return portfolios.get(name).distribution(date);
  }

  public BarGraph portfolioPerformance(String name, LocalDate start, LocalDate end,
                                       int xLabelCount) {
    return portfolios.get(name).performance(name, start, end, xLabelCount);
  }

  public void portfolioRebalance(String name, List<Integer> split, LocalDate date) {
    portfolios.get(name).rebalance(split, date, new StockDatabase());
  }

  public boolean isValidSplit(String name, List<String> split) {
    return portfolios.get(name).isValidSplit(split);
  }
}
