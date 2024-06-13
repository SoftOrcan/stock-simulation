import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.IStockBetterModel;
import model.IStockModel;
import model.StockBetterModel;
import model.StockModel;
import model.BarGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests and examples for StockModel class.
 */
public class IStockModelTest {
  IStockModel model;
  IStockBetterModel betterModel;

  /**
   * Set up the data to test.
   */
  @Before
  public void setUp() {
    File dataDirectory = new File("./data");
    if (!dataDirectory.exists()) {
      dataDirectory.mkdirs();
    }

    model = new StockModel();
    betterModel = new StockBetterModel();
  }

  private void clear(String dir) {
    String path = "./" + dir + "/";

    File directory = new File(path);

    File[] files = directory.listFiles();

    if (files != null) {
      for (File file : files) {
        try {
          if (file.isFile()) {
            // Empty the file
            file.delete();
            file.createNewFile();
          }
        } catch (IOException e) {
          // Handle IOException
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * This method tests the createPortfolio method in the StocksModel class.
   */
  @Test
  public void testCreatePortfolio() {
    model.createPortfolio("Portfolio One");
    assertEquals(0, model.portfolioValue("Portfolio One", LocalDate.of(2023, 6, 6)), 0.01);
    model.createPortfolio("Portfolio Two");
    assertEquals(0, model.portfolioValue("Portfolio Two", LocalDate.of(2023, 6, 6)), 0.01);
  }

  /**
   * This method tests the createPortfolio method multiple times in the StocksModel class.
   */
  @Test
  public void testCreatePortfolioMultiple() {
    betterModel.createPortfolio("TechStocks");
    betterModel.createPortfolio("StocksTwo");
    betterModel.createPortfolio("StocksThree");
    assertTrue(betterModel.hasPortfolio("TechStocks"));
    assertTrue(betterModel.hasPortfolio("StocksTwo"));
    assertTrue(betterModel.hasPortfolio("StocksThree"));
  }

  /**
   * Tests addStockToPortfolio on StockBetterModel
   */
  @Test
  public void testAddStockToBetterPortfolio() {
    clear("portfolios");
    clear("data");
    betterModel.createPortfolio("TechStocks");
    betterModel.addStockToPortfolio("TechStocks", "AAPL", 10, LocalDate.of(2023, 6, 1));
    double portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2023, 6, 2));
    // check for portfolio after add date
    assertEquals(1809.5, portfolioValue, 0.001);
    // check for portfolio before add date
    portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2023, 5, 2));
    assertEquals(0, portfolioValue, 0.001);
    // check for portfolio on add date
    portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2023, 6, 1));
    assertEquals(1800.9, portfolioValue, 0.001);
    // add multiple stocks so different stocks are in portfolio on differeent dates
    betterModel.addStockToPortfolio("TechStocks", "GOOG", 15, LocalDate.of(2022, 6, 1));
    betterModel.addStockToPortfolio("TechStocks", "GOOG", 15, LocalDate.of(2021, 2, 1));
    portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2022, 6, 1));
    assertEquals(69969.3, portfolioValue, 0.001);
    portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2021, 6, 1));
    assertEquals(37689.950000000004, portfolioValue, 0.001);
    portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2024, 6, 1));
    assertEquals(4531.9, portfolioValue, 0.001);
    portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2000, 6, 1));
    assertEquals(0, portfolioValue, 0.001);
  }

  /**
   * This method tests the addStockToPortfolio method.
   */
  @Test
  public void testAddStockToPortfolio() {
    model.createPortfolio("Portfolio One");
    model.addStockToPortfolio("Portfolio One", "AAPL", 10);
    assertEquals(1792.1, model.portfolioValue("Portfolio One", LocalDate.of(2023, 6, 6)), 0.01);
    // Add stock to another portfolio.
    model.createPortfolio("Portfolio Two");
    model.addStockToPortfolio("Portfolio Two", "GOOG", 10);
    assertEquals(1279.1, model.portfolioValue("Portfolio Two", LocalDate.of(2023, 6, 6)), 0.01);
    // Add stock to same portfolio.
    model.addStockToPortfolio("Portfolio Two", "AMZN", 3);
    model.addStockToPortfolio("Portfolio Two", "AAPL", 5);
    assertEquals(2554.98, model.portfolioValue("Portfolio Two", LocalDate.of(2023, 6, 6)), 0.01);
  }

  /**
   * Test remove stock from portfolio the better stock model.
   */
  @Test
  public void testRemoveStockFromBetterPortfolio() {
    betterModel.createPortfolio("TechStocks");
    betterModel.addStockToPortfolio("TechStocks", "AAPL", 10, LocalDate.of(2023, 6, 1));
    betterModel.removeStockFromPortfolio("TechStocks", "AAPL", 5, LocalDate.of(2023, 7, 2));
    double portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2023, 6, 1));
    assertEquals(10, portfolioValue, 0.001);
    betterModel.removeStockFromPortfolio("TechStocks", "AAPL", 5, LocalDate.of(2023, 6, 3));
    portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2023, 6, 3));
    assertEquals(0, portfolioValue, 0.01);
  }

  /**
   * This method tests the removeStockFromPortfolio method.
   */
  @Test
  public void testRemoveStockFromPortfolio() {
    model.createPortfolio("Portfolio One");
    model.addStockToPortfolio("Portfolio One", "AAPL", 10);
    assertEquals(1792.1, model.portfolioValue("Portfolio One", LocalDate.of(2023, 6, 6)), 0.01);
    // Remove stock.
    model.removeStockFromPortfolio("Portfolio One", "AAPL", 10);
    // Tests.
    assertEquals(0, model.portfolioValue("Portfolio One", LocalDate.of(2023, 6, 6)), 0.01);

    model.createPortfolio("Portfolio Two");
    model.addStockToPortfolio("Portfolio Two", "GOOG", 10);
    model.addStockToPortfolio("Portfolio Two", "AMZN", 3);
    model.addStockToPortfolio("Portfolio Two", "AAPL", 5);
    // Initialized data correctly.
    assertEquals(2554.98, model.portfolioValue("Portfolio Two", LocalDate.of(2023, 6, 6)), 0.01);
    // Remove stock.
    model.removeStockFromPortfolio("Portfolio Two", "GOOG", 10);
    assertEquals(1275.88, model.portfolioValue("Portfolio Two", LocalDate.of(2023, 6, 6)), 0.01);
  }

  /**
   * Test stock change on better model.
   */
  @Test
  public void testStockChange() {
    double change =
        betterModel.stockChange("AAPL", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 6, 1));
    // Check that the change is within a reasonable range
    assertEquals(50.16, change, 0.001);
    // check when second date is before
    change =
        betterModel.stockChange("AAPL", LocalDate.of(2023, 1, 1), LocalDate.of(2022, 6, 1));
    assertEquals(18.78, change, 0.001);
    // check when date is same
    change =
        betterModel.stockChange("AAPL", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 1));
    assertEquals(0, change, 0.001);
    // check when change is negative
    change =
        betterModel.stockChange("AAPL", LocalDate.of(2012, 1, 1), LocalDate.of(2023, 1, 1));
    assertEquals(-275.07, change, 0.001);
  }

  /**
   * This method tests the calculateChange method in the StocksModel class.
   */
  @Test
  public void testCalculateChange() {
    // Initialize data.
    model.createPortfolio("Portfolio One");
    model.createPortfolio("Portfolio Two");
    model.addStockToPortfolio("Portfolio Two", "AAPL", 10);
    // Test when change is negative.
    assertEquals(-0.6399999999999864,
        model.stockChange("AAPL", LocalDate.of(2024, 5, 3), LocalDate.of(2024, 5, 8)), 0.01);
    // Test when change is positive.
    assertEquals(9.509999999999991,
        model.stockChange("AAPL", LocalDate.of(2024, 5, 8), LocalDate.of(2024, 6, 2)), 0.01);
    // Test change over years.
    assertEquals(-174.81,
        model.stockChange("AAPL", LocalDate.of(2011, 3, 8), LocalDate.of(2023, 6, 2)), 0.01);
    // test change over months.
    assertEquals(-6.22999999999999,
        model.stockChange("AAPL", LocalDate.of(2022, 3, 8), LocalDate.of(2022, 6, 2)), 0.01);
  }

  /**
   * Test moving average on better model.
   */
  @Test
  public void testStockMovingAverage() {
    double movingAverage = betterModel.stockMovingAverage("AAPL", LocalDate.of(2023, 6, 1), 30);
    assertEquals(173.61816666666667, movingAverage, 0.001);
    movingAverage = betterModel.stockMovingAverage("GOOG", LocalDate.of(2015, 2, 1), 30);
    assertEquals(514.623, movingAverage, 0.001);
    movingAverage = betterModel.stockMovingAverage("AMZN", LocalDate.of(2018, 5, 2), 30);
    assertEquals(1482.8280000000009, movingAverage, 0.001);
  }

  /**
   * This method tests the movingAverage method in the StocksModel class.
   */
  @Test
  public void testMovingAverage() {
    model.createPortfolio("Portfolio One");
    model.addStockToPortfolio("Portfolio One", "AAPL", 10);
    model.addStockToPortfolio("Portfolio One", "GOOG", 100);
    model.addStockToPortfolio("Portfolio One", "AMZN", 50);
    assertEquals(175.93045454545452,
        model.stockMovingAverage("AAPL", LocalDate.of(2022, 1, 3), 22), 0.01);
    // Test over more than a year.
    assertEquals(15.722799999999985,
        model.stockMovingAverage("AMZN", LocalDate.of(2002, 12, 21), 400), 0.01);
    // Test over a month.
    assertEquals(179.5600000000001,
        model.stockMovingAverage("GOOG", LocalDate.of(2024, 10, 13), 32), 0.01);
  }

  /**
   * Test stock crossovers on better model.
   */
  @Test
  public void testStockCrossovers() {
    List<LocalDate> crossovers = betterModel.stockCrossovers("AAPL",
        LocalDate.of(2023, 1, 1),
        LocalDate.of(2023, 2, 1), 30);
    assertEquals(new ArrayList(Arrays.asList(LocalDate.of(2023, 1, 11),
        LocalDate.of(2023, 1, 12),
        LocalDate.of(2023, 1, 13),
        LocalDate.of(2023, 1, 14),
        LocalDate.of(2023, 1, 15),
        LocalDate.of(2023, 1, 16),
        LocalDate.of(2023, 1, 17),
        LocalDate.of(2023, 1, 18),
        LocalDate.of(2023, 1, 19),
        LocalDate.of(2023, 1, 20),
        LocalDate.of(2023, 1, 21),
        LocalDate.of(2023, 1, 22),
        LocalDate.of(2023, 1, 23),
        LocalDate.of(2023, 1, 24),
        LocalDate.of(2023, 1, 25),
        LocalDate.of(2023, 1, 26),
        LocalDate.of(2023, 1, 27),
        LocalDate.of(2023, 1, 28),
        LocalDate.of(2023, 1, 29),
        LocalDate.of(2023, 1, 30),
        LocalDate.of(2023, 1, 31))), crossovers);
  }


  /**
   * This method tests the findCrossovers method in the StocksModel class.
   */
  @Test
  public void testFindCrossovers() {
    model.createPortfolio("Portfolio One");
    model.addStockToPortfolio("Portfolio One", "AAPL", 10);
    model.addStockToPortfolio("Portfolio One", "AMZN", 20);
    model.addStockToPortfolio("Portfolio One", "GOOG", 30);
    assertEquals(Arrays.asList(
            LocalDate.of(2015, 4, 3),
            LocalDate.of(2015, 4, 4),
            LocalDate.of(2015, 4, 6),
            LocalDate.of(2015, 4, 7),
            LocalDate.of(2015, 4, 9),
            LocalDate.of(2015, 4, 10),
            LocalDate.of(2015, 4, 11),
            LocalDate.of(2015, 4, 12),
            LocalDate.of(2015, 4, 15),
            LocalDate.of(2015, 4, 20),
            LocalDate.of(2015, 4, 21),
            LocalDate.of(2015, 4, 22),
            LocalDate.of(2015, 4, 23),
            LocalDate.of(2015, 4, 24),
            LocalDate.of(2015, 4, 25),
            LocalDate.of(2015, 4, 26),
            LocalDate.of(2015, 4, 27),
            LocalDate.of(2015, 5, 1),
            LocalDate.of(2015, 5, 2)),
        model.stockCrossovers("AAPL",
            LocalDate.of(2015, 4, 3),
            LocalDate.of(2015, 5, 3), 4));
  }

  /**
   * This method tests portfolio value on the better model.
   */
  @Test
  public void testPortfolioValue() {
    clear("portfolios");
    clear("data");
    betterModel.createPortfolio("TechStocks");
    betterModel.addStockToPortfolio("TechStocks", "AAPL", 10, LocalDate.of(2023, 6, 1));
    betterModel.addStockToPortfolio("TechStocks", "GOOG", 5, LocalDate.of(2023, 6, 1));
    double portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2023, 6, 1));
    assertEquals(2422.75, portfolioValue, 0.01);
    portfolioValue = betterModel.portfolioValue("TechStocks", LocalDate.of(2000, 6, 1));
    assertEquals(0, portfolioValue, 0.01);
  }

  /**
   * This method tests the valueOfPortfolio method.
   */
  @Test
  public void testValueOfPortfolio() {
    model.createPortfolio("Portfolio One");
    model.addStockToPortfolio("Portfolio One", "AAPL", 10);
    // Test value of on current day.
    assertEquals(1792.1, model.portfolioValue("Portfolio One", LocalDate.of(2023, 6, 6)), 0.01);
    // Multiple months ago.
    assertEquals(1296.2, model.portfolioValue("Portfolio One", LocalDate.of(2023, 1, 6)), 0.01);
    // Multiple years ago.
    assertEquals(3337.3, model.portfolioValue("Portfolio One", LocalDate.of(2011, 1, 6)), 0.01);
    // Test for when there are multiple stocks in a portfolio.
    model.addStockToPortfolio("Portfolio One", "AMZN", 10);
    model.addStockToPortfolio("Portfolio One", "GOOG", 10);
    // Test value of current day.
    assertEquals(4337.299999999999,
        model.portfolioValue("Portfolio One", LocalDate.of(2023, 6, 6)), 0.01);
    // Multiple months ago.
    assertEquals(3038.6000000000004,
        model.portfolioValue("Portfolio One", LocalDate.of(2023, 1, 6)), 0.01);
    // Multiple years ago.
    assertEquals(14769.7, model.portfolioValue("Portfolio One", LocalDate.of(2016, 1, 6)), 0.01);
  }

  /**
   * This method tests portfolio composition on better model.
   */
  @Test
  public void testPortfolioComposition() {
    clear("portfolios");
    clear("data");
    betterModel.createPortfolio("TechStocks");
    betterModel.addStockToPortfolio("TechStocks", "AAPL", 10, LocalDate.of(2023, 6, 1));
    BarGraph composition = betterModel.portfolioComposition("TechStocks", LocalDate.of(2023, 6, 1));
    String result = composition.interpret();
    assertTrue(betterModel.portfolioComposition(
        "TechStocks", LocalDate.of(2023, 6, 1)).interpret().contains("AAPL"));
    // This is occurring because you didn't clear the portfolio, so TechStocks actually contains
    // more shares in AAPL than 10.
    assertTrue(betterModel.portfolioComposition(
        "TechStocks", LocalDate.of(2023, 6, 1)).interpret().contains("10"));
  }

  /**
   * This method tests portfolio distribution on better model.
   */
  @Test
  public void testPortfolioDistribution() {
    betterModel.createPortfolio("TechStocks");
    betterModel.addStockToPortfolio("TechStocks", "AAPL", 10, LocalDate.of(2023, 6, 1));
    BarGraph distribution =
        betterModel.portfolioDistribution("TechStocks", LocalDate.of(2023, 6, 1));
    String result = distribution.interpret();
    assertTrue(result.contains("AAPL"));
  }

  @Test
  public void testPortfolioPerformance() {
    betterModel.createPortfolio("TechStocks");
    betterModel.addStockToPortfolio("TechStocks", "AAPL", 10, LocalDate.of(2023, 6, 1));
    BarGraph performance =
        betterModel.portfolioPerformance(
            "TechStocks", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 6, 1), 5);
    String result = performance.interpret();
    assertTrue(result.contains("*"));
    assertTrue(result.contains("X-Axis Scale:"));
  }

  @Test
  public void testPortfolioRebalance() {
    betterModel.createPortfolio("TechStocks");
    betterModel.addStockToPortfolio("TechStocks", "AAPL", 10, LocalDate.of(2023, 6, 1));
    betterModel.portfolioRebalance("TechStocks", List.of(50, 50), LocalDate.of(2023, 6, 1));
    BarGraph distribution =
        betterModel.portfolioDistribution("TechStocks", LocalDate.of(2023, 6, 1));
    String result = distribution.interpret();
    assertTrue(result.contains("AAPL"));
  }

  @Test
  public void testIsValidSplit() {
    betterModel.createPortfolio("TechStocks");
    betterModel.addStockToPortfolio("TechStocks", "AAPL", 10, LocalDate.of(2023, 6, 1));
    boolean isValid = betterModel.isValidSplit("TechStocks", List.of("AAPL", "GOOG"));
    assertFalse(isValid);
    betterModel.addStockToPortfolio("TechStocks", "GOOG", 10, LocalDate.of(2023, 6, 1));
    isValid = betterModel.isValidSplit("TechStocks", List.of("AAPL"));
    assertFalse(isValid);
  }
}