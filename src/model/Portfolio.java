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

  /**
   * Package-private. Add a stock (termed as 'symbol') and the quantity of the stock to a
   * Portfolio.
   *
   * @param symbol   The stock to add.
   * @param quantity The quantity of stocks purchased.
   */
  void add(String symbol, Double quantity) {
    // Retrieve the current amount of a stock, if it exists.
    double currAmount = this.portfolio.getOrDefault(symbol, 0.0);
    portfolio.put(symbol, quantity + currAmount);
  }

  /**
   * Package-private. Remove a quantity of shares of a stock (termed as 'symbol') from a Portfolio.
   *
   * @param symbol The stock to remove.
   */
  void remove(String symbol, int amount) {
    // Retrieve the current amount of shares of the stock and compute the remaining amount after
    // deducting.
    double quantity = portfolio.get(symbol) - amount;
    // If the quantity of shares is greater than 0, then replace the stock with the new quantity.
    if (quantity > 0) {
      portfolio.put(symbol, quantity);
    }
    // Otherwise, remove the stock, since its current quantity is 0.
    else {
      portfolio.remove(symbol);
    }
  }

  /**
   * Package-private. Calculate the value of a Portfolio based on the stocks' value on a given date
   * (termed as 'date') and the quantity of each stock. Retrieve data relating to the stocks' value
   * via StockDatabase (termed as 'stockDatabase').
   *
   * @param date          The specified date to retrieve data regarding a stock on.
   * @param stockDatabase The database to retrieve information regarding a stock from.
   * @return The cumulative value of a Portfolio.
   */
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
