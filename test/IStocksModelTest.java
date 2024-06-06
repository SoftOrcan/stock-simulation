import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import model.IStocksModel;
import model.StocksModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class tests the functionality of the IStocksModel.
 */
public class IStocksModelTest {

  // portfolios

  IStocksModel stocksModelOne;

  /**
   * This method sets up the data to be tested on.
   */
  @Before
  public void setUp() {
    stocksModelOne = new StocksModel();
  }

  /**
   * This method tests the createPortfolio method in the StocksModel class.
   */
  @Test
  public void testCreatePortfolio() {
    stocksModelOne.createPortfolio("Portfolio One");
    assertNotNull(stocksModelOne);

  }

  /**
   * This method tests the addStockToPortfolio method.
   */
  @Test
  public void testAddStockToPortfolio() {
    stocksModelOne.createPortfolio("Portfolio One");
    stocksModelOne.addStockToPortfolio("Portfolio One", "AAPL", 10);
    assertEquals(1792.1, stocksModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // add stock to another portfolio
    stocksModelOne.createPortfolio("Portfolio Two");
    stocksModelOne.addStockToPortfolio("Portfolio Two", "GOOG", 10);
    assertEquals(1279.1, stocksModelOne.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
    // add stock to same portfolio
    stocksModelOne.addStockToPortfolio("Portfolio Two", "AMZN", 3);
    stocksModelOne.addStockToPortfolio("Portfolio Two", "AAPL", 5);
    assertEquals(2554.98, stocksModelOne.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
  }

  /**
   * This method tests the removeStockFromPortfolio method.
   */
  @Test
  public void testRemoveStockFromPortfolio() {
    stocksModelOne.createPortfolio("Portfolio One");
    stocksModelOne.addStockToPortfolio("Portfolio One", "AAPL", 10);
    assertEquals(1792.1, stocksModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // remove stock
    stocksModelOne.removeStockFromPortfolio("Portfolio One", "AAPL");
    // tests
    assertEquals(0, stocksModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);

    stocksModelOne.createPortfolio("Portfolio Two");
    stocksModelOne.addStockToPortfolio("Portfolio Two", "GOOG", 10);
    stocksModelOne.addStockToPortfolio("Portfolio Two", "AMZN", 3);
    stocksModelOne.addStockToPortfolio("Portfolio Two", "AAPL", 5);
    // intialized data correctly
    assertEquals(2554.98, stocksModelOne.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
    // remove stock
    stocksModelOne.removeStockFromPortfolio("Portfolio Two", "GOOG");
    assertEquals(1275.88, stocksModelOne.valueOfPortfolio("Portfolio Two",
            LocalDate.of(2023, 6, 6)), 0.01);
  }

  /**
   * This method tests the calculateChange method in the StocksModel class.
   */
  @Test
  public void testCalculateChange() {
    // initialize data
    stocksModelOne.createPortfolio("Portfolio One");
    stocksModelOne.createPortfolio("Portfolio Two");
    stocksModelOne.addStockToPortfolio("Portfolio Two", "AAPL", 10);
    // test when change is negative
    assertEquals(-0.6399999999999864, stocksModelOne.calculateChange("AAPL",
            LocalDate.of(2024, 5, 3),
            LocalDate.of(2024, 5, 8)), 0.01);
    // test when change is positive
    assertEquals(9.509999999999991, stocksModelOne.calculateChange("AAPL",
            LocalDate.of(2024, 5, 8),
            LocalDate.of(2024, 6, 2)), 0.01);
    // test change over years
    assertEquals(-174.81, stocksModelOne.calculateChange("AAPL",
            LocalDate.of(2011, 3, 8),
            LocalDate.of(2023, 6, 2)), 0.01);
    // test change over months
    assertEquals(-6.22999999999999, stocksModelOne.calculateChange("AAPL",
            LocalDate.of(2022, 3, 8),
            LocalDate.of(2022, 6, 2)), 0.01);
  }

  /**
   * This method tests the movingAverage method in the StocksModel class.
   */
  @Test
  public void testMovingAverage() {
    stocksModelOne.createPortfolio("Portfolio One");
    stocksModelOne.addStockToPortfolio("Portfolio One", "AAPL", 10);
    stocksModelOne.addStockToPortfolio("Portfolio One", "GOOG", 100);
    stocksModelOne.addStockToPortfolio("Portfolio One", "AMZN", 50);
    assertEquals(175.93045454545452, stocksModelOne.movingAverage("AAPL",
            LocalDate.of(2022, 1, 3),22), 0.01);
    // test over more than a year
    assertEquals(15.722799999999985, stocksModelOne.movingAverage("AMZN",
            LocalDate.of(2002, 12, 21),400), 0.01);
    // test over a month
    assertEquals(177.06999999999996, stocksModelOne.movingAverage("GOOG",
            LocalDate.of(2024, 10, 13),32), 0.01);
  }

  /**
   * This method tests the findCrossovers method in the StocksModel class.
   */
  @Test
  public void testFindCrossovers() {
    stocksModelOne.createPortfolio("Portfolio One");
    stocksModelOne.addStockToPortfolio("Portfolio One", "AAPL", 10);
    stocksModelOne.addStockToPortfolio("Portfolio One", "AMZN", 20);
    stocksModelOne.addStockToPortfolio("Portfolio One", "GOOG", 30);
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
            stocksModelOne.findCrossovers("AAPL",
            LocalDate.of(2015,4,3),
            LocalDate.of(2015,5,3), 4));
  }

  /**
   * This method tests the valueOfPortfolio method.
   */
  @Test
  public void testValueOfPortfolio() {
    stocksModelOne.createPortfolio("Portfolio One");
    stocksModelOne.addStockToPortfolio("Portfolio One", "AAPL", 10);
    // test value of on current day
    assertEquals(1792.1, stocksModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // multiple months ago
    assertEquals(1296.2, stocksModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 1, 6)), 0.01);
    // multiple years ago
    assertEquals(3337.3, stocksModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2011, 1, 6)), 0.01);
    // test for when there are multiple stocks in a portfolio
    stocksModelOne.addStockToPortfolio("Portfolio One", "AMZN", 10);
    stocksModelOne.addStockToPortfolio("Portfolio One", "GOOG", 10);
    // test value of current day
    assertEquals(4337.299999999999, stocksModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // multiple months ago
    assertEquals(3038.6000000000004, stocksModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2023, 1, 6)), 0.01);
    // multiple years ago
    assertEquals(14769.7, stocksModelOne.valueOfPortfolio("Portfolio One",
            LocalDate.of(2016, 1, 6)), 0.01);

  }

}