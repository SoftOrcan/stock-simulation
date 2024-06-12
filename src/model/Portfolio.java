package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Portfolio class represents a stock portfolio, containing stocks which the user has
 * purchased (via the 'add()' method). It is permissible for the user to remove a purchased stock
 * (via the 'remove()' method) and calculate the cumulative value of the portfolio based on the
 * prices of the stocks on a given date (via the 'value()' method).
 */
class Portfolio {
  private final Map<String, Double> portfolio;
  private final Map<String, StockPurchase> portfolioOnDate;

  /**
   * Package-private initial state of a Portfolio, represented as an empty HashMap.
   */
  Portfolio() {
    this.portfolio = new HashMap<>();
    this.portfolioOnDate = new HashMap<>();
  }

  /**
   * Package-private. Add a stock (termed as 'symbol') and the quantity of the stock to a Portfolio.
   *
   * @param symbol   The stock to add.
   * @param quantity The quantity of stocks purchased.
   */
  void add(String symbol, Double quantity) {
    portfolio.put(symbol, quantity);
  }


  void addOnDate(String symbol, int quantity, LocalDate date) {
    portfolioOnDate.put(symbol, new StockPurchase(quantity, date));
  }

  void remove(String symbol, int amount) {
    double quantity = portfolio.get(symbol) - amount;
    if (quantity > 0) {
      portfolio.put(symbol, quantity);
    }
    portfolio.remove(symbol);
  }

  /**
   * Package-private. Remove a stock (termed as 'symbol') from a Portfolio.
   *
   * @param symbol The stock to remove.
   */
  void removeOnDate(String symbol, int amount, LocalDate date) {
    double quantity = portfolioOnDate.get(symbol).getAmount() - amount;
    if (quantity > 0) {
      portfolioOnDate.put(symbol, new StockPurchase(quantity, date));
    }
    portfolioOnDate.remove(symbol);
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

    Map.Entry<String, StockPurchase> firstPurchase = portfolioOnDate.entrySet().iterator().next();
    if (firstPurchase.getValue().getPurchaseDate().isAfter(date)) {
      return total;
    }

    // For every value in the HashMap...
    for (String symbol : portfolioOnDate.keySet()) {
      // Find the price of a stock on the given date and multiply it by the quantity of stock
      // purchased.
      total += portfolioOnDate.get(symbol).getAmount() *
              stockDatabase.getPriceOn(symbol, String.valueOf(date), "");
    }

    return total;
  }

  BarGraph composition(LocalDate date) {
    BarGraph graph = new BarGraph("Composition:");
    if (portfolioOnDate.isEmpty()) {
      return graph;
    }
    graph.createEntries(date, portfolioOnDate.entrySet(), true);
    return graph;
  }

  BarGraph distribution(LocalDate date) {
    BarGraph graph = new BarGraph("Distribution:");
    if (portfolioOnDate.isEmpty()) {
      return graph;
    }
    graph.createEntries(date, portfolioOnDate.entrySet(), false);
    return graph;
  }

  BarGraph performance(String name, LocalDate start, LocalDate end, int xLabelCount) {
    BarGraph graph = new BarGraph("Performance of '" + name + "':");
    graph.createXAxis(start, end, xLabelCount);

    HashMap<String, Double> stocks = new HashMap<>();
    for (Map.Entry<String, StockPurchase> entry : portfolioOnDate.entrySet()) {
      stocks.put(entry.getKey(), entry.getValue().getAmount());
    }

    graph.createPortfolioYAxis(stocks, start, end);
    return graph;
  }

  void rebalance(List<Integer> split, LocalDate date, StockDatabase stockDatabase) {
    double portfolioValue = value(date, stockDatabase);
    int index = 0;
    double rebalancedPrice;
    double rebalancedShares;
    String stock;
    double stockPrice;

    for (Map.Entry<String, StockPurchase> entry : portfolioOnDate.entrySet()) {
      rebalancedPrice = portfolioValue * split.get(index) * 0.01;
      stock = entry.getKey();
      stockPrice = stockDatabase.getPriceOn(stock, String.valueOf(date), "");
      rebalancedShares = rebalancedPrice / stockPrice;

      portfolioOnDate.put(stock, new StockPurchase(rebalancedShares, entry.getValue().purchaseDate));
      index += 1;
    }
  }

  boolean isValidSplit(List<String> split) {
    if (split.size() != portfolioOnDate.size()) {
      return false;
    }

    try {
      for (String value : split) {
        Integer.parseInt(value);
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
