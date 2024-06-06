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
        LocalDate date = LocalDate.parse(values[0]); // parses correctly!
        double open = Double.parseDouble(values[1]);
        double close = Double.parseDouble(values[4]);
        prices.put(date, new StockPrice(open, close));
      }
    } catch (IOException e) {
      logger.severe("Error reading file: " + e.getMessage());
    }
  }

  protected double calculateChange(LocalDate startDate, LocalDate endDate) {
    return getPriceOn(endDate) - getPriceOn(startDate);
  }

  protected double movingAverage(LocalDate date, int numDays) {
    double total = 0;

    for (int i = 0; i < numDays; i++) {
      total += getPriceOn(date.minusDays(i));
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
    StockPrice price;
    while ((price = prices.get(date)) == null) {
      date = date.minusDays(1);
    }
    return price.closePrice;
  }
}