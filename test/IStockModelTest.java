import java.time.LocalDate;
import java.util.Arrays;
import model.IStockModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is an abstract class that implements test methods for the model tests.
 */
public abstract class IStockModelTest {


  /**
   * This method tests the createPortfolio method in the StocksModel class.
   */
  public void testCreatePortfolio(IStockModel model) {
    assertTrue(model.hasPortfolio("TechStocks"));
    assertTrue(model.hasPortfolio("StocksTwo"));
    assertTrue(model.hasPortfolio("StocksThree"));
  }

  /**
   * This method tests the calculate change method.
   * @param model IStock model
   */
  public void testCalculateChange(IStockModel model) {
    // Test when change is negative.
    assertEquals(-0.6399999999999864, model.stockChange("AAPL",
            LocalDate.of(2024, 5, 3),
            LocalDate.of(2024, 5, 8)), 0.01);
    // Test when change is positive.
    assertEquals(9.509999999999991, model.stockChange("AAPL",
            LocalDate.of(2024, 5, 8),
            LocalDate.of(2024, 6, 2)), 0.01);
    // Test change over years.
    assertEquals(-174.81, model.stockChange("AAPL",
            LocalDate.of(2011, 3, 8),
            LocalDate.of(2023, 6, 2)), 0.01);
    // test change over months.
    assertEquals(-6.22999999999999, model.stockChange("AAPL",
            LocalDate.of(2022, 3, 8),
            LocalDate.of(2022, 6, 2)), 0.01);
  }

  /**
   * This method tests the moving average method.
   * @param model IStockModel model
   */
  public void testMovingAverage(IStockModel model) {
    assertEquals(175.93045454545452, model.stockMovingAverage("AAPL",
            LocalDate.of(2022, 1, 3), 22), 0.01);
    // Test over more than a year.
    assertEquals(15.722799999999985, model.stockMovingAverage("AMZN",
            LocalDate.of(2002, 12, 21), 400), 0.01);
    // Test over a month.
    assertEquals(176.4499999999999, model.stockMovingAverage("GOOG",
            LocalDate.of(2024, 10, 13), 32), 0.01);
  }

  /**
   * This method tests the find crossovers method.
   * @param model IStockModel model
   */
  public void testFindCrossovers(IStockModel model) {
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
   * This method tests the value of portfolio method.
   * @param model IStockModel model
   */
  public void testValueOfPortfolio(IStockModel model) {
    // Test value of on current day.
    assertEquals(1792.1, model.portfolioValue("Portfolio One",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Multiple months ago.
    assertEquals(1296.2, model.portfolioValue("Portfolio One",
            LocalDate.of(2023, 1, 6)), 0.01);
    // Multiple years ago.
    assertEquals(3337.3, model.portfolioValue("Portfolio One",
            LocalDate.of(2011, 1, 6)), 0.01);


  }

}