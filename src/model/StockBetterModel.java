package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class StockBetterModel extends StockModel implements IStockBetterModel {
  private final Logger logger = Logger.getLogger(StockBetterModel.class.getName());
  // Portfolios owned, represented as a HashMap<PortfolioName (String), Portfolio>.
  private final Map<String, Portfolio> portfolios;
  // The stock database to retrieve information relating to a stock from.
  private final StockDatabase stockDatabase;

  public StockBetterModel() {
    portfolios = new HashMap<>();
    retrieveFiles();
    stockDatabase = new StockDatabase();
  }

  private void retrieveFiles() {
    File folder = new File("./portfolios/");
    File[] files = folder.listFiles();

    if (files != null) {
      for (File file : files) {
        if (file.isFile()) {
          String name = file.getName().replaceFirst("[.][^.]+$", "");
          portfolios.put(name, new Portfolio());
          portfolios.get(name).retrieveData(name);
        }
      }
    }
  }

  public boolean hasPortfolio(String name) {
    return portfolios.containsKey(name);
  }

  public void createPortfolio(String name) {
    File portfolioDir = new File("./portfolios");
    if (!portfolioDir.exists()) {
      portfolioDir.mkdirs();
    }

    File portfolioFile = new File(portfolioDir, name + ".csv");
    try {
      portfolioFile.createNewFile();
    } catch (IOException e) {
      logger.severe("IO Exception: " + e.getMessage());
    }

    retrieveFiles();
  }

  public void addStockToPortfolio(String name, String symbol, int quantity, LocalDate date) {
    portfolios.get(name).addToFile(name, symbol, quantity, date);
  }

  public void removeStockFromPortfolio(String name, String symbol, int quantity, LocalDate date) {
    portfolios.get(name).removeFromFile(name, symbol, quantity, date);
  }

  public BarGraph stockPerformance(String symbol, LocalDate start, LocalDate end, int xLabelCount) {
    return stockDatabase.performance(symbol, start, end, xLabelCount);
  }

  public BarGraph portfolioComposition(String name, LocalDate date) {
    return portfolios.get(name).composition(name, date);
  }

  public BarGraph portfolioDistribution(String name, LocalDate date) {
    return portfolios.get(name).distribution(name, date);
  }

  public BarGraph portfolioPerformance(String name, LocalDate start, LocalDate end,
                                       int xLabelCount) {
    return portfolios.get(name).performance(name, start, end, xLabelCount);
  }

  public void portfolioRebalance(String name, List<Integer> split, LocalDate date) {
    portfolios.get(name).rebalance(name, split, date, new StockDatabase());
  }

  public boolean isValidSplit(String name, List<String> split) {
    return portfolios.get(name).isValidSplit(split);
  }
}
