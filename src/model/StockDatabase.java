package model;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The StockDatabase class
 */
class StockDatabase {
  // Logger to record functionality or capture thrown exceptions.
  private final Logger logger = Logger.getLogger(StockDatabase.class.getName());
  private final AlphaVantageAPI api = new AlphaVantageAPI();

  private StockInformation getStockInformation(String symbol) {
    // check if file exists
    for (File f : Objects.requireNonNull(new File("./data").listFiles())) {
      if (f.getName().equals(symbol + ".csv")) {
        // file exists
        return new StockInformation(symbol);
      }
    }
    api.getData(symbol);
    // file now exists?
    return new StockInformation(symbol);
    // TODO - error handling
  }

  double calculateChange(String symbol, LocalDate startDate, LocalDate endDate) {
    return getStockInformation(symbol).calculateChange(startDate, endDate);
  }

  double movingAverage(String symbol, LocalDate date, int x) {
    return getStockInformation(symbol).movingAverage(date, x);
  }

  List<LocalDate> findCrossovers(String symbol, LocalDate startDate, LocalDate endDate, int x) {
    return getStockInformation(symbol).findCrossovers(startDate, endDate, x);
  }

  double getPriceOf(String symbol, LocalDate date) {
    return getStockInformation(symbol).getPriceOn(date);
  }
}
