package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The Portfolio class represents a stock portfolio, containing stocks which the user has purchased
 * (via the 'add()' method). It is permissible for the user to remove a purchased stock (via the
 * 'remove()' method) and calculate the cumulative value of the portfolio based on the prices of the
 * stocks on a given date (via the 'value()' method).
 */
class Portfolio {
  private final Map<String, Double> portfolio;

  /**
   * Package-private initial state of a Portfolio, represented as an empty HashMap.
   */
  Portfolio() {
    this.portfolio = new HashMap<>();
  }

  void add(String symbol, Double quantity) {
    double currAmount = this.portfolio.getOrDefault(symbol, 0.0);
    portfolio.put(symbol, quantity + currAmount);
  }

  void remove(String symbol, int amount) {
    double quantity = portfolio.get(symbol) - amount;
    if (quantity > 0) {
      portfolio.put(symbol, quantity);
    }
    portfolio.remove(symbol);
  }

  double value(LocalDate date, StockDatabase stockDatabase) {
    double total = 0;
    // For every value in the HashMap...
    for (String symbol : portfolio.keySet()) {
      // Find the price of a stock on the given date and multiply it by the quantity of stock
      // purchased.
      total += portfolio.get(symbol) * stockDatabase.getPriceOn(symbol, String.valueOf(date), "");
    }
    return total;
  }
}
