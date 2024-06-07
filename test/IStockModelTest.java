import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import model.IStockModel;
import model.StockModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class tests the functionality of the IStocksModel. Logs a severe message with the
 * exception message.
 */
public class IStockModelTest {
  IStockModel stockModelOne;

  /**
   * Set up the data to test.
   */
  @Before
  public void setUp() {
    stockModelOne = new StockModel();
  }

  /**
   * This method tests the createPortfolio method in the StocksModel class.
   */
  @Test
  public void testCreatePortfolio() {
    stockModelOne.createPortfolio("Portfolio One");
    assertNotNull(stockModelOne);

  }

  /**
   * This method tests the addStockToPortfolio method.
   */
  @Test
  public void testAddStockToPortfolio() {
    stockModelOne.createPortfolio("Portfolio One");
    stockModelOne.addStockToPortfolio("Portfolio One", "AAPL", 10);
    assertEquals(1792.1, stockModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Add stock to another portfolio.
    stockModelOne.createPortfolio("Portfolio Two");
    stockModelOne.addStockToPortfolio("Portfolio Two", "GOOG", 10);
    assertEquals(1279.1, stockModelOne.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Add stock to same portfolio.
    stockModelOne.addStockToPortfolio("Portfolio Two", "AMZN", 3);
    stockModelOne.addStockToPortfolio("Portfolio Two", "AAPL", 5);
    assertEquals(2554.98, stockModelOne.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
  }

  /**
   * This method tests the removeStockFromPortfolio method.
   */
  @Test
  public void testRemoveStockFromPortfolio() {
    stockModelOne.createPortfolio("Portfolio One");
    stockModelOne.addStockToPortfolio("Portfolio One", "AAPL", 10);
    assertEquals(1792.1, stockModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Remove stock.
    stockModelOne.removeStockFromPortfolio("Portfolio One", "AAPL");
    // Tests.
    assertEquals(0, stockModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);

    stockModelOne.createPortfolio("Portfolio Two");
    stockModelOne.addStockToPortfolio("Portfolio Two", "GOOG", 10);
    stockModelOne.addStockToPortfolio("Portfolio Two", "AMZN", 3);
    stockModelOne.addStockToPortfolio("Portfolio Two", "AAPL", 5);
    // Initialized data correctly.
    assertEquals(2554.98, stockModelOne.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Remove stock.
    stockModelOne.removeStockFromPortfolio("Portfolio Two", "GOOG");
    assertEquals(1275.88, stockModelOne.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
  }

  /**
   * This method tests the calculateChange method in the StocksModel class.
   */
  @Test
  public void testCalculateChange() {
    // Initialize data.
    stockModelOne.createPortfolio("Portfolio One");
    stockModelOne.createPortfolio("Portfolio Two");
    stockModelOne.addStockToPortfolio("Portfolio Two", "AAPL", 10);
    // Test when change is negative.
    assertEquals(-0.6399999999999864, stockModelOne.calculateChange("AAPL",
            LocalDate.of(2024, 5, 3),
            LocalDate.of(2024, 5, 8)), 0.01);
    // Test when change is positive.
    assertEquals(9.509999999999991, stockModelOne.calculateChange("AAPL",
            LocalDate.of(2024, 5, 8),
            LocalDate.of(2024, 6, 2)), 0.01);
    // Test change over years.
    assertEquals(-174.81, stockModelOne.calculateChange("AAPL",
            LocalDate.of(2011, 3, 8),
            LocalDate.of(2023, 6, 2)), 0.01);
    // test change over months.
    assertEquals(-6.22999999999999, stockModelOne.calculateChange("AAPL",
            LocalDate.of(2022, 3, 8),
            LocalDate.of(2022, 6, 2)), 0.01);
  }

  /**
   * This method tests the movingAverage method in the StocksModel class.
   */
  @Test
  public void testMovingAverage() {
    stockModelOne.createPortfolio("Portfolio One");
    stockModelOne.addStockToPortfolio("Portfolio One", "AAPL", 10);
    stockModelOne.addStockToPortfolio("Portfolio One", "GOOG", 100);
    stockModelOne.addStockToPortfolio("Portfolio One", "AMZN", 50);
    assertEquals(175.93045454545452, stockModelOne.movingAverage("AAPL",
            LocalDate.of(2022, 1, 3), 22), 0.01);
    // Test over more than a year.
    assertEquals(15.722799999999985, stockModelOne.movingAverage("AMZN",
            LocalDate.of(2002, 12, 21), 400), 0.01);
    // Test over a month.
    assertEquals(177.06999999999996, stockModelOne.movingAverage("GOOG",
            LocalDate.of(2024, 10, 13), 32), 0.01);
  }

  /**
   * This method tests the findCrossovers method in the StocksModel class.
   */
  @Test
  public void testFindCrossovers() {
    stockModelOne.createPortfolio("Portfolio One");
    stockModelOne.addStockToPortfolio("Portfolio One", "AAPL", 10);
    stockModelOne.addStockToPortfolio("Portfolio One", "AMZN", 20);
    stockModelOne.addStockToPortfolio("Portfolio One", "GOOG", 30);
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
            stockModelOne.findCrossovers("AAPL",
                    LocalDate.of(2015, 4, 3),
                    LocalDate.of(2015, 5, 3), 4));
  }

  /**
   * This method tests the valueOfPortfolio method.
   */
  @Test
  public void testValueOfPortfolio() {
    stockModelOne.createPortfolio("Portfolio One");
    stockModelOne.addStockToPortfolio("Portfolio One", "AAPL", 10);
    // Test value of on current day.
    assertEquals(1792.1, stockModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Multiple months ago.
    assertEquals(1296.2, stockModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 1, 6)), 0.01);
    // Multiple years ago.
    assertEquals(3337.3, stockModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2011, 1, 6)), 0.01);
    // Test for when there are multiple stocks in a portfolio.
    stockModelOne.addStockToPortfolio("Portfolio One", "AMZN", 10);
    stockModelOne.addStockToPortfolio("Portfolio One", "GOOG", 10);
    // Test value of current day.
    assertEquals(4337.299999999999, stockModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Multiple months ago.
    assertEquals(3038.6000000000004, stockModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 1, 6)), 0.01);
    // Multiple years ago.
    assertEquals(14769.7, stockModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2016, 1, 6)), 0.01);

  }
}