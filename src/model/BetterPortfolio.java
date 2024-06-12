package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BetterPortfolio extends Portfolio {
  private final Logger logger = Logger.getLogger(BetterPortfolio.class.getName());
  private final Map<String, StockPurchase> portfolioFile;

  BetterPortfolio() {
    this.portfolioFile = new HashMap<>();
  }

  double value(LocalDate date, StockDatabase stockDatabase) {
    double total = 0;

    Map.Entry<String, StockPurchase> firstPurchase = portfolioFile.entrySet().iterator().next();
    if (firstPurchase.getValue().getPurchaseDate().isAfter(date)) {
      return total;
    }

    // For every value in the HashMap...
    for (String symbol : portfolioFile.keySet()) {
      // Find the price of a stock on the given date and multiply it by the quantity of stock
      // purchased.
      total += portfolioFile.get(symbol).getAmount() *
          stockDatabase.getPriceOn(symbol, String.valueOf(date), "");
    }

    return total;
  }

  void retrieveData(String name) {
    File file = new File("./portfolios/" + name + ".csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] columns = line.split(",");
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

  void addStockToFile(String name, String symbol, double quantity, LocalDate date) {
    double currAmount = portfolioFile.get(symbol).getAmount();
    portfolioFile.put(symbol, new StockPurchase(currAmount + quantity, date));
    copyToFile(name);
  }

  void removeStockFromFile(String name, String symbol, double quantity, LocalDate date) {
    double amount = portfolioFile.get(symbol).getAmount() - quantity;
    if (amount > 0) {
      portfolioFile.put(symbol, new StockPurchase(amount, date));
    }
    portfolioFile.remove(symbol);
    copyToFile(name);
  }

  BarGraph composition(LocalDate date) {
    BarGraph graph = new BarGraph("Composition:");
    if (portfolioFile.isEmpty()) {
      return graph;
    }
    graph.createEntries(date, portfolioFile.entrySet(), true);
    return graph;
  }

  BarGraph distribution(LocalDate date) {
    BarGraph graph = new BarGraph("Distribution:");
    if (portfolioFile.isEmpty()) {
      return graph;
    }
    graph.createEntries(date, portfolioFile.entrySet(), false);
    return graph;
  }

  BarGraph performance(String name, LocalDate start, LocalDate end, int xLabelCount) {
    BarGraph graph = new BarGraph("Performance of '" + name + "':");
    graph.createXAxis(start, end, xLabelCount);

    HashMap<String, Double> stocks = new HashMap<>();
    for (Map.Entry<String, StockPurchase> entry : portfolioFile.entrySet()) {
      stocks.put(entry.getKey(), entry.getValue().getAmount());
    }

    graph.createPortfolioYAxis(stocks, start, end);
    return graph;
  }

  void rebalance(String name, List<Integer> split, LocalDate date, StockDatabase stockDatabase) {
    double portfolioValue = value(date, stockDatabase);
    int index = 0;
    double rebalancedPrice;
    double rebalancedShares;
    String stock;
    double stockPrice;

    for (Map.Entry<String, StockPurchase> entry : portfolioFile.entrySet()) {
      rebalancedPrice = portfolioValue * split.get(index) * 0.01;
      stock = entry.getKey();
      stockPrice = stockDatabase.getPriceOn(stock, String.valueOf(date), "");
      rebalancedShares = rebalancedPrice / stockPrice;

      portfolioFile.put(stock,
          new StockPurchase(rebalancedShares, entry.getValue().getPurchaseDate()));
      index += 1;
    }

    copyToFile(name);
  }

  boolean isValidSplit(List<String> split) {
    if (split.size() != portfolioFile.size()) {
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
