import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import model.IStockModel;


/**
 * The MockStockModel class is a mock for IStockModel.
 */
public class MockStockModel implements IStockModel {
  private final List<String> log;


  MockStockModel() {
    log = new ArrayList<>();
  }


  List<String> getLog() {
    return log;
  }


  public double stockChange(String symbol, LocalDate startDate, LocalDate endDate) {
    log.add(String.format("calculateChange: %s, %s, %s", symbol, startDate, endDate));
    return 0;
  }


  public double stockMovingAverage(String symbol, LocalDate date, int numDays) {
    log.add(String.format("movingAverage: %s, %s, %d", symbol, date, numDays));
    return 0;
  }


  public List<LocalDate> stockCrossovers(String symbol, LocalDate startDate, LocalDate endDate,
                                         int x) {
    log.add(String.format("findCrossovers: %s, %s, %s, %d", symbol, startDate, endDate, x));
    return new ArrayList<>();
  }


  public boolean isValidSymbol(String symbol) {
    return symbol.equals("AAPL");
  }


  public void createPortfolio(String portfolioName) {
    // Mock implementation for createPortfolio.
    log.add(String.format("createPortfolio: %s", portfolioName));
  }


  public void addStockToPortfolio(String portfolioName, String symbol, double quantity) {
    // Mock implementation for addStockToPortfolio.
    log.add(String.format("addStockToPortfolio: %s, %s, %f", portfolioName, symbol, quantity));
  }


  public void removeStockFromPortfolio(String portfolioName, String symbol, int quantity) {
    // Mock implementation for removeStockFromPortfolio.
    log.add(String.format("removeStockFromPortfolio: %s, %s, %d", portfolioName, symbol, quantity));
  }


  public double portfolioValue(String portfolioName, LocalDate date) {
    // Return a mock value for valueOfPortfolio.
    log.add(String.format("valueOfPortfolio: %s, %s", portfolioName, date));
    return 0;
  }


  public boolean hasPortfolio(String portfolioName) {
    return portfolioName.equals("MyPortfolio");
  }

  @Override
  public List<String> getPortfolios() {
    return List.of();
  }
}
