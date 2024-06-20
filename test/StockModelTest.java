import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import java.time.LocalDate;

import model.IStockModel;
import model.StockModel;

/**
 * This class tests the stock model.
 */
public class StockModelTest extends IStockModelTest{
  IStockModel model;
  IStockModel secondModel;
  IStockModel thirdModel;
  IStockModel fourthModel;
  IStockModel fifthModel;

  @Before
  public void setUp() throws Exception {
    model = new StockModel();
    secondModel = new StockModel();
    thirdModel = new StockModel();
    fourthModel = new StockModel();
    fifthModel = new StockModel();

    model.createPortfolio("TechStocks");
    model.createPortfolio("StocksTwo");
    model.createPortfolio("StocksThree");

    secondModel.createPortfolio("Portfolio One");
    secondModel.createPortfolio("Portfolio Two");
    secondModel.addStockToPortfolio("Portfolio Two", "AAPL", 10);

    thirdModel.createPortfolio("Portfolio One");
    thirdModel.addStockToPortfolio("Portfolio One", "AAPL", 10);
    thirdModel.addStockToPortfolio("Portfolio One", "GOOG", 100);
    thirdModel.addStockToPortfolio("Portfolio One", "AMZN", 50);

    fourthModel.createPortfolio("Portfolio One");
    fourthModel.addStockToPortfolio("Portfolio One", "AAPL", 10);
    fourthModel.addStockToPortfolio("Portfolio One", "AMZN", 20);
    fourthModel.addStockToPortfolio("Portfolio One", "GOOG", 30);

    fifthModel.createPortfolio("Portfolio One");
    fifthModel.addStockToPortfolio("Portfolio One", "AAPL", 10);
  }


  @Test
  public void testCreatePortfolioM() {
    super.testCreatePortfolio(model);
  }


  @Test
  public void testCalculateChangeM() {
    super.testCalculateChange(secondModel);
  }

  @Test
  public void testMovingAverageM() {
    super.testMovingAverage(thirdModel);
  }

  @Test
  public void testFindCrossoversM() {
    testFindCrossovers(fourthModel);
  }

  @Test
  public void testValueOfPortfolioM() {
    super.testValueOfPortfolio(fifthModel);
  }


  /**
   * This method tests the addStockToPortfolio method.
   */
  @Test
  public void testAddStockToPortfolio() {
    model.createPortfolio("Portfolio Three");
    model.addStockToPortfolio("Portfolio Three", "AAPL", 10);
    assertEquals(1792.1, model.portfolioValue("Portfolio Three",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Add stock to another portfolio.
    model.createPortfolio("Portfolio Four");
    model.addStockToPortfolio("Portfolio Four", "GOOG", 10);
    assertEquals(1279.1, model.portfolioValue("Portfolio Four",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Add stock to same portfolio.
    model.addStockToPortfolio("Portfolio Four", "AMZN", 3);
    model.addStockToPortfolio("Portfolio Four", "AAPL", 5);
    assertEquals(2554.98, model.portfolioValue("Portfolio Four",
            LocalDate.of(2023, 6, 6)), 0.01);
  }


  /**
   * This method tests the removeStockFromPortfolio method.
   */
  @Test
  public void testRemoveStockFromPortfolio() {
    model.createPortfolio("Portfolio Three");
    model.addStockToPortfolio("Portfolio Three", "AAPL", 10);
    assertEquals(1792.1, model.portfolioValue("Portfolio Three",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Remove stock.
    model.removeStockFromPortfolio("Portfolio Three", "AAPL", 10);
    // Tests.
    assertEquals(0, model.portfolioValue("Portfolio Three",
            LocalDate.of(2023, 6, 6)), 0.01);


    model.createPortfolio("Portfolio Four");
    model.addStockToPortfolio("Portfolio Four", "GOOG", 10);
    model.addStockToPortfolio("Portfolio Four", "AMZN", 3);
    model.addStockToPortfolio("Portfolio Four", "AAPL", 5);
    // Initialized data correctly.
    assertEquals(2554.98, model.portfolioValue("Portfolio Four",
            LocalDate.of(2023, 6, 6)), 0.01);
    // Remove stock.
    model.removeStockFromPortfolio("Portfolio Four", "GOOG", 10);
    assertEquals(1275.88, model.portfolioValue("Portfolio Four",
            LocalDate.of(2023, 6, 6)), 0.01);
  }


}
