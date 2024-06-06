package model;

import java.time.LocalDate;
import java.util.List;

public class MockStocksModel implements IStocksModel {

  @Override
  public double calculateChange(String symbol, LocalDate startDate, LocalDate endDate) {
    // Return a mock value for calculateChange
    return 100.0;
  }

  @Override
  public double movingAverage(String symbol, LocalDate date, int numDays) {
    // Return a mock value for movingAverage
    return 50.0;
  }

  @Override
  public List<LocalDate> findCrossovers(String symbol, LocalDate startDate, LocalDate endDate, int x) {
    // Return a mock list of dates for findCrossovers
    return List.of(LocalDate.now(), LocalDate.now().minusDays(1));
  }

  @Override
  public void createPortfolio(String portfolioName) {
    // Mock implementation for createPortfolio
  }

  @Override
  public void addStockToPortfolio(String portfolioName, String symbol, int amount) {
    // Mock implementation for addStockToPortfolio
  }

  @Override
  public void removeStockFromPortfolio(String portfolioName, String symbol) {
    // Mock implementation for removeStockFromPortfolio
  }

  @Override
  public double valueOfPortfolio(String portfolioName, LocalDate date) {
    // Return a mock value for valueOfPortfolio
    return 500.0;
  }
}
