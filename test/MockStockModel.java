import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.IStockModel;

class MockStockModel implements IStockModel {

  public double calculateChange(String symbol, LocalDate startDate, LocalDate endDate) {
    // Return a mock value for calculateChange.
    return 0;
  }

  public double movingAverage(String symbol, LocalDate date, int numDays) {
    // Return a mock value for movingAverage.
    return 0;
  }

  public List<LocalDate> findCrossovers(String symbol, LocalDate startDate, LocalDate endDate,
                                        int x) {
    // Return a mock list of dates for findCrossovers.
    return new ArrayList<>();
  }

  public boolean validSymbol(String symbol) {
    return symbol.equals("AAPL");
  }

  public void createPortfolio(String portfolioName) {
    // Mock implementation for createPortfolio.
  }

  public void addStockToPortfolio(String portfolioName, String symbol, int amount) {
    // Mock implementation for addStockToPortfolio.
  }

  public void removeStockFromPortfolio(String portfolioName, String symbol) {
    // Mock implementation for removeStockFromPortfolio.
  }

  public double valueOfPortfolio(String portfolioName, LocalDate date) {
    // Return a mock value for valueOfPortfolio.
    return 0;
  }

  public boolean hasPortfolio(String portfolioName) {
    return portfolioName.equals("MyPortfolio");
  }
}
