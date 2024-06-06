package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

class StockInformation {
  private final Logger logger = Logger.getLogger(StockInformation.class.getName());
  private final Map<LocalDate, StockPrice> prices;

  public StockInformation(String symbol) {
    this.prices = new HashMap<>();
    this.getHistoricalPrices(symbol);
  }

  private void getHistoricalPrices(String symbol) {
    try (BufferedReader br = new BufferedReader(new FileReader("./data/" + symbol + ".csv"))) {
      String line;
      br.readLine(); // Skip header
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        String date = values[0];
        double open = Double.parseDouble(values[1]);
        double close = Double.parseDouble(values[4]);
        prices.add(new StockPrice(date, open, close));
      }
    } catch (IOException e) {
      logger.severe("Error reading file: " + e.getMessage());
    }
  }

  // method (haven't been implemented)

  protected double calculateChange(LocalDate startDate, LocalDate endDate) {
    return prices.get(endDate).closePrice - prices.get(startDate).closePrice;
  }

  protected double movingAverage(LocalDate date, int numDays) {
    double total = 0;

    for (int i = 0; i < numDays; i++) {
      total += prices.get(date.minusDays(i)).closePrice;
    }

    return total / numDays;
  }

  protected List<LocalDate> findCrossovers(LocalDate startDate, LocalDate endDate, int x) {
    LocalDate date = startDate;
    List<LocalDate> crossovers = new ArrayList<>();

    while (!date.isEqual(endDate)) {
      if (getPriceOn(date) > movingAverage(date, x)) {
        crossovers.add(date);
      }
      date = date.plusDays(1);
    }

    return crossovers;
  }

  public double getPriceOn(LocalDate date) {
    return prices.get(date).closePrice;
  }
}