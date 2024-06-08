import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import model.IStockModel;
import model.StockModel;

import static org.junit.Assert.assertEquals;

/**
 * Tests and examples for StockModel class.
 */
public class StockModelTest {
  IStockModel model;

  /**
   * Set up the data to test.
   */
  @Before
  public void setUp() {
    model = new StockModel();
  }

  /**
   * This method tests the createPortfolio method in the StocksModel class.
   */
  @Test
  public void testCreatePortfolio() {
    model.createPortfolio("Portfolio One");
    assertEquals(0, model.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    model.createPortfolio("Portfolio Two");
    assertEquals(0, model.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
  }

  /**
   * This method tests the addStockToPortfolio method.
   */
  @Test
  public void testAddStockToPortfolio() {
    model.createPortfolio("Portfolio One");
    model.addStockToPortfolio("Portfolio One", "AAPL", 10);
    assertEquals(1792.1, model.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Add stock to another portfolio.
    model.createPortfolio("Portfolio Two");
    model.addStockToPortfolio("Portfolio Two", "GOOG", 10);
    assertEquals(1279.1, model.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Add stock to same portfolio.
    model.addStockToPortfolio("Portfolio Two", "AMZN", 3);
    model.addStockToPortfolio("Portfolio Two", "AAPL", 5);
    assertEquals(2554.98, model.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
  }

  /**
   * This method tests the removeStockFromPortfolio method.
   */
  @Test
  public void testRemoveStockFromPortfolio() {
    model.createPortfolio("Portfolio One");
    model.addStockToPortfolio("Portfolio One", "AAPL", 10);
    assertEquals(1792.1, model.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Remove stock.
    model.removeStockFromPortfolio("Portfolio One", "AAPL");
    // Tests.
    assertEquals(0, model.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);

    model.createPortfolio("Portfolio Two");
    model.addStockToPortfolio("Portfolio Two", "GOOG", 10);
    model.addStockToPortfolio("Portfolio Two", "AMZN", 3);
    model.addStockToPortfolio("Portfolio Two", "AAPL", 5);
    // Initialized data correctly.
    assertEquals(2554.98, model.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Remove stock.
    model.removeStockFromPortfolio("Portfolio Two", "GOOG");
    assertEquals(1275.88, model.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
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
    assertEquals(-0.6399999999999864, model.calculateChange("AAPL",
            LocalDate.of(2024, 5, 3),
            LocalDate.of(2024, 5, 8)), 0.01);
    // Test when change is positive.
    assertEquals(9.509999999999991, model.calculateChange("AAPL",
            LocalDate.of(2024, 5, 8),
            LocalDate.of(2024, 6, 2)), 0.01);
    // Test change over years.
    assertEquals(-174.81, model.calculateChange("AAPL",
            LocalDate.of(2011, 3, 8),
            LocalDate.of(2023, 6, 2)), 0.01);
    // test change over months.
    assertEquals(-6.22999999999999, model.calculateChange("AAPL",
            LocalDate.of(2022, 3, 8),
            LocalDate.of(2022, 6, 2)), 0.01);
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
    assertEquals(175.93045454545452, model.movingAverage("AAPL",
            LocalDate.of(2022, 1, 3), 22), 0.01);
    // Test over more than a year.
    assertEquals(15.722799999999985, model.movingAverage("AMZN",
            LocalDate.of(2002, 12, 21), 400), 0.01);
    // Test over a month.
    assertEquals(177.06999999999996, model.movingAverage("GOOG",
            LocalDate.of(2024, 10, 13), 32), 0.01);
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
            model.findCrossovers("AAPL",
                    LocalDate.of(2015, 4, 3),
                    LocalDate.of(2015, 5, 3), 4));
  }

  /**
   * This method tests the valueOfPortfolio method.
   */
  @Test
  public void testValueOfPortfolio() {
    model.createPortfolio("Portfolio One");
    model.addStockToPortfolio("Portfolio One", "AAPL", 10);
    // Test value of on current day.
    assertEquals(1792.1, model.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Multiple months ago.
    assertEquals(1296.2, model.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 1, 6)), 0.01);
    // Multiple years ago.
    assertEquals(3337.3, model.valueOfPortfolio("Portfolio One",
            LocalDate.of(2011, 1, 6)), 0.01);
    // Test for when there are multiple stocks in a portfolio.
    model.addStockToPortfolio("Portfolio One", "AMZN", 10);
    model.addStockToPortfolio("Portfolio One", "GOOG", 10);
    // Test value of current day.
    assertEquals(4337.299999999999, model.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Multiple months ago.
    assertEquals(3038.6000000000004, model.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 1, 6)), 0.01);
    // Multiple years ago.
    assertEquals(14769.7, model.valueOfPortfolio("Portfolio One",
            LocalDate.of(2016, 1, 6)), 0.01);

  }
}