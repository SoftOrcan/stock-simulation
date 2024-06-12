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
 * The Portfolio class represents a stock portfolio, containing stocks which the user has purchased
 * (via the 'add()' method). It is permissible for the user to remove a purchased stock (via the
 * 'remove()' method) and calculate the cumulative value of the portfolio based on the prices of the
 * stocks on a given date (via the 'value()' method).
 */
class Portfolio {
  private final Logger logger = Logger.getLogger(Portfolio.class.getName());
  private final Map<String, Double> portfolio;
  private final Map<String, StockPurchase> portfolioFile;

  /**
   * Package-private initial state of a Portfolio, represented as an empty HashMap.
   */
  Portfolio() {
    this.portfolio = new HashMap<>();
    this.portfolioFile = new HashMap<>();
  }

  void add(String symbol, Double quantity) {
    portfolio.put(symbol, quantity);
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

  private void copyToFile(String name) {
    File file = new File("./portfolios/" + name + ".csv");

    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
      for (Map.Entry<String, StockPurchase> entry : portfolioFile.entrySet()) {
        writer.println(entry.getKey() + ","
            + entry.getValue().getAmount() + "," + entry.getValue().getPurchaseDate());
      }
    } catch (IOException e) {
      logger.severe("IO Exception: " + e.getMessage());
    }
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

  void addToFile(String name, String symbol, double quantity, LocalDate date) {
    modifyFile(new File("./portfolios/" + name + ".csv"), symbol, quantity, date, false);
    retrieveData(name);
  }

  void removeFromFile(String name, String symbol, double quantity, LocalDate date) {
    modifyFile(new File("./portfolios/" + name + ".csv"), symbol, quantity, date, true);
    retrieveData(name);
  }

  void modifyFile(File file, String symbol, double quantity, LocalDate date, boolean isRemoving) {
    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      boolean found = false;

      while ((line = reader.readLine()) != null) {
        String[] columns = line.split(",");
        if (columns[0].equals(symbol)) {
          if (isRemoving) {
            double newAmount = Double.parseDouble(columns[1]) - quantity;
            if (newAmount > 0) {
              columns[1] = String.valueOf(newAmount);
            } else {
              found = true;
              continue;
            }
          } else {
            columns[1] = String.valueOf(Double.parseDouble(columns[1]) + quantity);
            LocalDate updatedDate = LocalDate.parse(columns[2]);
            columns[2] = updatedDate.isAfter(date) ? date.toString() : updatedDate.toString();
          }
          found = true;
        }
        lines.add(String.join(",", columns));
      }
      if (!found && !isRemoving) {
        lines.add(symbol + "," + quantity + "," + date);
      }
    } catch (IOException e) {
      logger.severe("IO Exception: " + e.getMessage());
    }

    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
      for (String line : lines) {
        writer.println(line);
      }
    } catch (IOException e) {
      logger.severe("IO Exception: " + e.getMessage());
    }
  }

  BarGraph composition(String name, LocalDate date) {
    BarGraph graph = new BarGraph("Composition:");
    if (portfolioFile.isEmpty()) {
      return graph;
    }
    graph.createEntries(date, portfolioFile.entrySet(), true);
    return graph;
  }

  BarGraph distribution(String name, LocalDate date) {
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

      portfolioFile.put(stock, new StockPurchase(rebalancedShares, entry.getValue().purchaseDate));
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
