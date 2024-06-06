package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The Portfolio class represents a stock portfolio, containing stocks which the user has
 * purchased (via the 'add()' method). It is permissible for the user to remove a purchased stock
 * (via the 'remove()' method) and calculate the cumulative value of the portfolio based on the
 * prices of the stocks on a given date (via the 'value()' method).
 */
class Portfolio {
  // A portfolio is a hashmap <Symbol, AmountPurchased>, represented as a <String, Integer>.
  private final Map<String, Integer> portfolio;

  /**
   * Package-private initial state of a Portfolio, represented as an empty HashMap.
   */
  Portfolio() {
    // The initial state of a Portfolio is an empty HashMap.
    this.portfolio = new HashMap<>();
  }

  /**
   * Package-private. Add a stock (termed as 'symbol') and the quantity of the stock to a Portfolio.
   *
   * @param symbol   The stock to add.
   * @param quantity The quantity of stocks purchased.
   */
  void add(String symbol, int quantity) {
    portfolio.put(symbol, quantity);
  }

  /**
   * Package-private. Remove a stock (termed as 'symbol') from a Portfolio.
   *
   * @param symbol The stock to remove.
   */
  void remove(String symbol) {
    portfolio.remove(symbol);
  }

  /**
   * Package-private. Calculate the value of a Portfolio based on the stocks' value on a given date
   * (termed as 'date') and the quantity of each stock. Retrieve data relating to the stocks'
   * value via StockDatabase (termed as 'stockDatabase').
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
      total += portfolio.get(symbol) * stockDatabase.getPriceOf(symbol, date);
    }
    return total;
  }
}
