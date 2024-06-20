package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The BetterPortfolio class represents a stock portfolio, containing stocks which the user has
 * purchased (via the 'add()' method). It is permissible for the user to remove a purchased stock
 * (via the 'remove()' method) and calculate the cumulative value of the portfolio based on the
 * prices of the stocks on a given date (via the 'value()' method).
 *
 * <p>The difference between the BetterPortfolio and Portfolio classes is BetterPortfolio's
 * additional functionalities. Such that, it is able to rebalance, ensuring the weights of each
 * stock is equivalent (via the 'rebalance()' method). Furthermore, it is able to display the
 * composition and distribution of the stocks, rendering the quantity of shares and the total price
 * of every stock, respectively. Most importantly, the BetterPortfolio class is able to reflect the
 * changes made internally directly to the .csv file of the portfolio.</p>
 */
public class BetterPortfolio extends Portfolio {
  // Logger to record functionality or capture thrown exceptions.
  private final Logger logger = Logger.getLogger(BetterPortfolio.class.getName());
  // A portfolio is represented as a hashmap <Symbol, StockPurchase>.
  private final Map<String, StockPurchase> portfolioFile;

  /**
   * Package-private initial state of a BetterPortfolio, represented as an empty HashMap.
   */
  BetterPortfolio() {
    this.portfolioFile = new HashMap<>();
  }

  /**
   * Package-private. Calculate the value of a BetterPortfolio based on the stocks' value on a given
   * date (termed as 'date') and the quantity of each stock. Retrieve data relating to the stocks'
   * value via StockDatabase (termed as 'stockDatabase').
   *
   * @param date          The specified date to retrieve data regarding a stock on.
   * @param stockDatabase The database to retrieve information regarding a stock from.
   * @return The cumulative value of a BetterPortfolio.
   */
  double value(LocalDate date, StockDatabase stockDatabase) {
    double total = 0;

    // For every value in the HashMap...
    for (Map.Entry<String, StockPurchase> entry : portfolioFile.entrySet()) {
      String symbol = entry.getKey();
      StockPurchase stockPurchase = entry.getValue();
      // Only calculate the prices of the stocks purchased before the provided date.
      if (stockPurchase.getPurchaseDate().isBefore(date) ||
          stockPurchase.getPurchaseDate().isEqual(date)) {
        // Find the price of a stock on the given date and multiply it by the quantity of stock
        // purchased.
        double price = stockDatabase.getPriceOn(symbol, String.valueOf(date), "");
        total += stockPurchase.getAmount() * price;
      }
    }

    return total;
  }

  /**
   * Retrieve the stocks from a portfolio represented as a .csv format.
   *
   * @param name The name of the portfolio to examine.
   */
  void retrieveData(String name) {
    // Open the .csv file based on the portfolio name.
    File file = new File("./portfolios/" + name + ".csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      // For every value in the .csv file...
      while ((line = reader.readLine()) != null) {
        String[] columns = line.split(",");
        // ... Add the value to portfolioFile.
        portfolioFile.put(
            columns[0],
            new StockPurchase(Double.parseDouble(columns[1]), LocalDate.parse(columns[2])));
      }
    } catch (IOException e) {
      logger.severe("IO Exception: " + e.getMessage());
    }
  }

  private void copyToFile(String name) {
    File file = new File("./portfolios/" + name + ".csv");

    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
      for (Map.Entry<String, StockPurchase> entry : portfolioFile.entrySet()) {
        writer.println(entry.getKey() + ","
            + entry.getValue().getAmount() + "," + entry.getValue().getPurchaseDate());
      }
      writer.flush();
    } catch (IOException e) {
      logger.severe("IO Exception: " + e.getMessage());
    }
  }

  /**
   * Package-private. Add a stock (termed as 'symbol') and the quantity of the stock to a Portfolio
   * on a specified date. Furthermore, reflect the changes to the .csv representation of the
   * portfolio.
   *
   * @param name     The name of the portfolio.
   * @param symbol   The stock to add.
   * @param quantity The quantity of stocks purchased.
   * @param date     The date of the purchase of the stock.
   */
  void addStockToFile(String name, String symbol, double quantity, LocalDate date) {
    // Retrieve the current amount of a stock, if it exists.
    double currAmount = portfolioFile.getOrDefault(
        symbol, new StockPurchase(0.0, LocalDate.parse("2024-01-01"))).getAmount();
    portfolioFile.put(symbol, new StockPurchase(currAmount + quantity, date));
    // Copy the updated HashMap representation of a portfolio to the .csv representation of a
    // portfolio.
    copyToFile(name);
  }

  /**
   * Package-private. Remove a quantity of shares of a stock (termed as 'symbol') from a Portfolio.
   *
   * @param name     The name of the portfolio.
   * @param symbol   The stock to remove.
   * @param quantity The quantity of stock to remove.
   * @param date     The date of the removal of the stock.
   */
  void removeStockFromFile(String name, String symbol, double quantity, LocalDate date) {
    // Retrieve the current amount of shares of the stock and compute the remaining amount after
    // deducting.
    double amount = portfolioFile.get(symbol).getAmount() - quantity;
    // If the quantity of shares is greater than 0, then replace the stock with the new quantity.
    if (amount > 0) {
      portfolioFile.put(symbol,
          new StockPurchase(amount, portfolioFile.get(symbol).getPurchaseDate()));
    }
    // Otherwise, remove the stock, since its current quantity is 0.
    else {
      portfolioFile.remove(symbol);
    }
    // Copy the updated HashMap representation of a portfolio to the .csv representation of a
    // portfolio.
    copyToFile(name);
  }

  /**
   * Visualize the composition of a portfolio (i.e. the quantity of each stock in the portfolio) on
   * a given date.
   *
   * @param date The date to examine.
   * @return A textual visualization of the quantity of each stock in the portfolio.
   */
  BarGraph composition(String name, LocalDate date) {
    BarGraph graph = new BarGraph("Composition of Portfolio '" + name + "':");
    if (portfolioFile.isEmpty()) {
      return graph;
    }
    graph.createEntries(date, portfolioFile.entrySet(), true);
    return graph;
  }

  /**
   * Visualize the distribution of a portfolio (i.e. the value of each stock in the portfolio) on a
   * given date.
   *
   * @param date The date to examine.
   * @return A textual visualization of the value of each stock in the portfolio.
   */
  BarGraph distribution(LocalDate date) {
    BarGraph graph = new BarGraph("Distribution:");
    if (portfolioFile.isEmpty()) {
      return graph;
    }
    graph.createEntries(date, portfolioFile.entrySet(), false);
    return graph;
  }

  /**
   * Visualize the performance of a portfolio over a time period. The quantity of x-axis labels is
   * based on the parameter 'xLabelCount'.
   *
   * @param name        The name of the portfolio.
   * @param start       The start date to examine.
   * @param end         The end date to examine.
   * @param xLabelCount The quantity of x-axis labels.
   * @return Textual visualization of the portfolio over a time period.
   */
  BarGraph performance(String name, LocalDate start, LocalDate end, int xLabelCount) {
    BarGraph graph = new BarGraph("Performance of '" + name + "':");
    graph.createXAxis(start, end, xLabelCount);

    // Create a HashMap containing the stocks in the portfolio and the quantity of each stock.
    HashMap<String, Double> stocks = new HashMap<>();
    for (Map.Entry<String, StockPurchase> entry : portfolioFile.entrySet()) {
      stocks.put(entry.getKey(), entry.getValue().getAmount());
    }

    graph.createPortfolioYAxis(stocks, start, end);
    return graph;
  }

  /**
   * Rebalance a portfolio to ensure that the weight of each stock is equivalent. The weight is
   * based on the parameter 'split'.
   *
   * @param name          The name of the portfolio.
   * @param split         The weight of each stock.
   * @param date          The date to examine.
   * @param stockDatabase The StockDatabase to retrieve the prices of the stocks.
   */
  void rebalance(String name, List<Integer> split, LocalDate date, StockDatabase stockDatabase) {
    double portfolioValue = value(date, stockDatabase); // The current value of the portfolio.
    int index = 0;
    double rebalancedPrice; // The re-computed price of each stock, considering the weight.
    double rebalancedShares; // The re-computed shares of each stock.
    String stock; // The stock in the HashMap.
    double stockPrice; // The current price of each stock in the HashMap.

    for (Map.Entry<String, StockPurchase> entry : portfolioFile.entrySet()) {
      // Calculate the intended value of each stock.
      rebalancedPrice = portfolioValue * split.get(index) * 0.01;
      stock = entry.getKey();
      // Calculate the current price of each stock.
      stockPrice = stockDatabase.getPriceOn(stock, String.valueOf(date), "");
      // Re-compute the quantity of shares each stock should be.
      rebalancedShares = rebalancedPrice / stockPrice;

      portfolioFile.put(stock,
          new StockPurchase(rebalancedShares, entry.getValue().getPurchaseDate()));
      index += 1;
    }

    // Copy the updated HashMap representation of a portfolio to the .csv representation of a
    // portfolio.
    copyToFile(name);
  }

  /**
   * Validate the user input, ensuring that every stock in the portfolio contains a weight and
   * each value is numerical.
   *
   * @param split The weight of each stock in the portfolio.
   * @return If the user input contains numerical values, and provides a weight to every stock.
   */
  boolean isValidSplit(List<String> split) {
    // If a weight is not provided for every stock, return false.
    if (split.size() != portfolioFile.size()) {
      return false;
    }

    int sum = 0;
    try {
      // If the values in the split is non-numerical, return false.
      for (String value : split) {
        sum += Integer.parseInt(value);
      }

      // The total of the splits must equate to 100.
      return sum == 100;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
