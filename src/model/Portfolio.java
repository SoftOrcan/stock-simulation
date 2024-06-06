package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
  Map<String, Integer> portfolio;

  Portfolio() {
    this.portfolio = new HashMap<>();
  }

  Portfolio(HashMap<String, Integer> portfolio) {
    this.portfolio = portfolio;
  }

  void add(String symbol, int quantity) {
    portfolio.put(symbol, quantity);
  }

  void remove(String symbol) {
    portfolio.remove(symbol);
  }

  double value(LocalDate date, StockDatabase stockDatabase) {
    double total = 0;
    for (String symbol : portfolio.keySet()) {
      total += portfolio.get(symbol) * stockDatabase.getPriceOf(symbol, date);
    }
    return total;
  }
}
