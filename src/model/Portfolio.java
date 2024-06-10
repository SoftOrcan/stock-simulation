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
  private final Map<String, StockPurchase> portfolio;
  private final StockDatabase stockDatabase;

  /**
   * Package-private initial state of a Portfolio, represented as an empty HashMap.
   */
  Portfolio() {
    // The initial state of a Portfolio is an empty HashMap.
    this.portfolio = new HashMap<>();
    this.stockDatabase = new StockDatabase();
  }

  /**
   * Package-private. Add a stock (termed as 'symbol') and the quantity of the stock to a Portfolio.
   *
   * @param symbol   The stock to add.
   * @param quantity The quantity of stocks purchased.
   */
  void add(String symbol, int quantity, LocalDate date) {
    portfolio.put(symbol, new StockPurchase(quantity, date));
  }

  /**
   * Package-private. Remove a stock (termed as 'symbol') from a Portfolio.
   *
   * @param symbol The stock to remove.
   */
  void remove(String symbol, int amount, LocalDate date) {
    int quantity = portfolio.get(symbol).getAmount() - amount;
    // TODO - maybe remove?
    if (quantity > 0) {
      portfolio.put(symbol, new StockPurchase(quantity, date));
    }
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

    Map.Entry<String, StockPurchase> firstPurchase = portfolio.entrySet().iterator().next();
    if (firstPurchase.getValue().getPurchaseDate().isAfter(date)) {
      return total;
    }

    // For every value in the HashMap...
    for (String symbol : portfolio.keySet()) {
      // Find the price of a stock on the given date and multiply it by the quantity of stock
      // purchased.
      total += portfolio.get(symbol).getAmount() *
              stockDatabase.getPriceOn(symbol, String.valueOf(date), "");
    }

    return total;
  }

  BarGraph composition(LocalDate date) {
    BarGraph graph = new BarGraph("Composition:");
    return portfolio.isEmpty() ? graph : createEntries(date, graph, true);
  }

  BarGraph distribution(LocalDate date) {
    BarGraph graph = new BarGraph("Distribution:");
    return portfolio.isEmpty() ? graph : createEntries(date, graph, false);
  }

  private BarGraph createEntries(LocalDate date, BarGraph graph, boolean isComposition) {
    for (Map.Entry<String, StockPurchase> entry : portfolio.entrySet()) {
      String stock = entry.getKey();
      StockPurchase purchase = entry.getValue();
      if (purchase.getPurchaseDate().isBefore(date) || purchase.getPurchaseDate().equals(date)) {
        String xAxisValue = isComposition ? stock : entry.getKey();
        String yAxisValue = isComposition ?
                purchase.getAmount() + " shares." :
                "$" + stockDatabase.getPriceOn(stock, String.valueOf(date), "");
        graph.xAxis.add(xAxisValue);
        graph.yAxis.add(yAxisValue);
      }
    }
    return graph;
  }

  BarGraph performance(String name, LocalDate start, LocalDate end, int xLabelCount) {
    BarGraph graph = new BarGraph("Performance of '" + name + "':");
    graph.createXAxis(start, end, xLabelCount);

    HashMap<String, Integer> stocks = new HashMap<>();
    for (Map.Entry<String, StockPurchase> entry : portfolio.entrySet()) {
      stocks.put(entry.getKey(), entry.getValue().getAmount());
    }

    graph.createPortfolioYAxis(stocks, start, end);
    return graph;
  }
}
