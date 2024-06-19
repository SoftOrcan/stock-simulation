package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The StockBetterModel class represents the "backend" of StockStimulation. Such that, it contains
 * implemented methods to (inheriting from the 'StockModel' class)...
 * <li>Calculate the net profit/loss of a stock over a time period (via the 'calculateChange()'
 * method).</li>
 * <li>The moving average of a stock over a time period (via the 'movingAverage ()' method).</li>
 * <li>The crossovers of a stock over a time period (via the 'findCrossovers()' method).</li>
 *
 * <p> Additionally, the StockBetterModel class has capabilities to...</p>
 * <li>Create (via the 'createPortfolio()' method)</li>
 * <li>Modify (via the 'addStockToPortfolio()' and 'removeStockFromPortfolio()' methods)</li>
 * <li>Calculate the value of a portfolio based on the stocks purchased and the price of the
 * stocks on a given date (via the 'valueOfPortfolio()' method).</li>
 * <li>Display the quantity of each stock in the portfolio (via the 'portfolioComposition()'
 * method)</li>
 * <li>Display the value of each stock in the portfolio (via the 'portfolioDistribution()'
 * method)</li>
 * <li>Rebalance the stocks in a portfolio based on the given weights (via the
 * 'portfolioRebalance()' method) .</li>
 */
public class StockBetterModel extends StockModel implements IStockBetterModel {
  private final Logger logger = Logger.getLogger(StockBetterModel.class.getName());
  // Portfolios owned, represented as a HashMap<PortfolioName (String), Portfolio>.
  private final Map<String, BetterPortfolio> portfolios;
  // The stock database to retrieve information relating to a stock from.
  private final StockDatabase stockDatabase;

  /**
   * Public constructor (utilized by IStockBetterController) that initializes the StockBetterModel.
   */
  public StockBetterModel() {
    portfolios = new HashMap<>();
    // Retrieve pre-existing files from previous runs.
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
          portfolios.put(name, new BetterPortfolio());
          portfolios.get(name).retrieveData(name);
        }
      }
    }
  }

  /**
   * Determine if a portfolio exists.
   *
   * @param name The name of the portfolio.
   * @return If a portfolio exists or not.
   */
  public boolean hasPortfolio(String name) {
    return portfolios.containsKey(name);
  }

  @Override
  public List<String> getPortfolios() {
    return new ArrayList<>(portfolios.keySet());
  }

  /**
   * Create a portfolio with a specified name. A portfolio is utilized to contain shares of one or
   * more stock. Furthermore, add the portfolio as a .csv file to the 'portfolios' directory.
   *
   * @param name The name of the portfolio.
   */
  public void createPortfolio(String name) {
    // If the 'portfolios' directory does not exist, add it.
    File portfolioDir = new File("./portfolios");
    if (!portfolioDir.exists()) {
      portfolioDir.mkdirs();
    }

    // Create the file and add it to the directory.
    File portfolioFile = new File(portfolioDir, name + ".csv");
    try {
      portfolioFile.createNewFile();
    } catch (IOException e) {
      logger.severe("IO Exception: " + e.getMessage());
    }

    // Add the portfolio to the HashMap.
    portfolios.put(name, new BetterPortfolio());
    // Retrieve any data from the .csv files existing previously.
    retrieveFiles();
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
  public void addStockToPortfolioFile(String name, String symbol, int quantity, LocalDate date) {
    portfolios.get(name).addStockToFile(name, symbol, quantity, date);
  }

  /**
   * Package-private. Remove a quantity of shares of a stock (termed as 'symbol') from a Portfolio.
   *
   * @param name     The name of the portfolio.
   * @param symbol   The stock to remove.
   * @param quantity The quantity of stock to remove.
   * @param date     The date of the removal of the stock.
   */
  public void removeStockFromPortfolioFile(String name, String symbol, int quantity,
                                           LocalDate date) {
    portfolios.get(name).removeStockFromFile(name, symbol, quantity, date);
  }

  /**
   * Package-private. Calculate the value of a portfolio based on the stocks' value on a given date
   * (termed as 'date') and the quantity of each stock.
   *
   * @param name The name of a portfolio.
   * @param date The specified date to retrieve data regarding a stock on.
   * @return The cumulative value of a BetterPortfolio.
   */
  public double portfolioValue(String name, LocalDate date) {
    return portfolios.get(name).value(date, stockDatabase);
  }

  /**
   * Visualize the performance of a stock over a time period. The quantity of x-intervals is based
   * on the parameter 'xLabelCount'.
   *
   * @param symbol      The stock symbol to examine.
   * @param start       The start date to examine.
   * @param end         The end date to examine.
   * @param xLabelCount The quantity of x-intervals to construct.
   * @return A textual visualization of a stock over a time period.
   */
  public BarGraph stockPerformance(String symbol, LocalDate start, LocalDate end, int xLabelCount) {
    return stockDatabase.performance(symbol, start, end, xLabelCount);
  }

  /**
   * Visualize the composition of a portfolio (i.e. the quantity of each stock in the portfolio) on
   * a given date.
   *
   * @param name The name of a portfolio.
   * @param date The date to examine.
   * @return A textual visualization of the quantity of each stock in the portfolio.
   */
  public BarGraph portfolioComposition(String name, LocalDate date) {
    return portfolios.get(name).composition(name, date);
  }

  /**
   * Visualize the distribution of a portfolio (i.e. the value of each stock in the portfolio) on a
   * given date.
   *
   * @param name The name of a portfolio.
   * @param date The date to examine.
   * @return A textual visualization of the value of each stock in the portfolio.
   */
  public BarGraph portfolioDistribution(String name, LocalDate date) {
    return portfolios.get(name).distribution(date);
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
  public BarGraph portfolioPerformance(String name, LocalDate start, LocalDate end,
                                       int xLabelCount) {
    return portfolios.get(name).performance(name, start, end, xLabelCount);
  }

  /**
   * Rebalance a portfolio to ensure that the weight of each stock is equivalent. The weight is
   * based on the parameter 'split'.
   *
   * @param name          The name of the portfolio.
   * @param split         The weight of each stock.
   * @param date          The date to examine.
   */
  public void portfolioRebalance(String name, List<Integer> split, LocalDate date) {
    portfolios.get(name).rebalance(name, split, date, new StockDatabase());
  }

  /**
   * Validate the user input, ensuring that every stock in the portfolio contains a weight and
   * each value is numerical.
   *
   * @param name The name of the portfolio.
   * @param split The weight of each stock in the portfolio.
   * @return If the user input contains numerical values, and provides a weight to every stock.
   */
  public boolean isValidSplit(String name, List<String> split) {
    return portfolios.get(name).isValidSplit(split);
  }
}
