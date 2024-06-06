package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StocksModel implements IStocksModel {
  private final Map<String, Portfolio> portfolios;
  private final StockDatabase stockDatabase;

  public StocksModel() {
    portfolios = new HashMap<>();
    stockDatabase = new StockDatabase();
  }

  public double calculateChange(String symbol, LocalDate startDate, LocalDate endDate) {
    return stockDatabase.calculateChange(symbol, startDate, endDate);
  }

  public double movingAverage(String symbol, LocalDate date, int numDays) {
    return stockDatabase.movingAverage(symbol, date, numDays);
  }

  public List<LocalDate> findCrossovers(String symbol, LocalDate startDate, LocalDate endDate,
                                        int numDays) {
    return stockDatabase.findCrossovers(symbol, startDate, endDate, numDays);
  }

  public void createPortfolio(String portfolioName) {
    portfolios.put(portfolioName, new Portfolio());
  }

  public void addStockToPortfolio(String portfolioName, String symbol, int amount) {
    portfolios.get(portfolioName).add(symbol, amount);
  }

  public void removeStockFromPortfolio(String portfolioName, String symbol) {
    portfolios.get(portfolioName).remove(symbol);
  }

  public double valueOfPortfolio(String portfolioName, LocalDate date) {
    return portfolios.get(portfolioName).value(date, stockDatabase);
  }
}
