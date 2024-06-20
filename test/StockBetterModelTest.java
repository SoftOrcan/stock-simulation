import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


import model.BarGraph;
import model.IStockBetterModel;
import model.StockBetterModel;

import static org.junit.Assert.*;

/**
 * This class tests the better stock model.
 */
public class StockBetterModelTest extends IStockModelTest {

  IStockBetterModel model2;
  IStockBetterModel secondModel2;
  IStockBetterModel thirdModel2;
  IStockBetterModel fourthModel2;
  IStockBetterModel fifthModel2;
  IStockBetterModel betterModel2;


  @Before
  public void setUp() throws Exception {
    model2 = new StockBetterModel();
    secondModel2 = new StockBetterModel();
    thirdModel2 = new StockBetterModel();
    fourthModel2 = new StockBetterModel();
    fifthModel2 = new StockBetterModel();
    betterModel2 = new StockBetterModel();


    model2.createPortfolio("TechStocks");
    model2.createPortfolio("StocksTwo");
    model2.createPortfolio("StocksThree");


    secondModel2.createPortfolio("Portfolio One");
    secondModel2.createPortfolio("Portfolio Two");
    secondModel2.addStockToPortfolioFile("Portfolio Two", "AAPL", 10,
            LocalDate.of(2000, 01, 01));


    thirdModel2.createPortfolio("Portfolio One");
    thirdModel2.addStockToPortfolioFile("Portfolio One", "AAPL", 10,
            LocalDate.of(2000, 01, 01));
    thirdModel2.addStockToPortfolioFile("Portfolio One", "GOOG", 100,
            LocalDate.of(2000, 01, 01));
    thirdModel2.addStockToPortfolioFile("Portfolio One", "AMZN", 50,
            LocalDate.of(2000, 01, 01));


    fourthModel2.createPortfolio("Portfolio One");
    fourthModel2.addStockToPortfolioFile("Portfolio One", "AAPL", 10,
            LocalDate.of(2000, 01, 01));
    fourthModel2.addStockToPortfolioFile("Portfolio One", "AMZN", 20,
            LocalDate.of(2000, 01, 01));
    fourthModel2.addStockToPortfolioFile("Portfolio One", "GOOG", 30,
            LocalDate.of(2000, 01, 01));


    fifthModel2.createPortfolio("Portfolio One");
    fifthModel2.addStockToPortfolioFile("Portfolio One", "AAPL", 10,
            LocalDate.of(2000, 01, 01));
  }


  /**
   * Test case for creating portfolios using {@link IStockModel#createPortfolio(String)}.
   */
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


  @Test
  public void testCreatePortfolioM() {
    super.testCreatePortfolio(model2);
  }


  @Test
  public void testCalculateChangeM() {
    super.testCalculateChange(secondModel2);
  }


  @Test
  public void testMovingAverageM() {
    super.testMovingAverage(thirdModel2);
  }


  @Test
  public void testFindCrossoversM() {
    testFindCrossovers(fourthModel2);
  }


  /**
   * This method tests portfolio value on the better model.
   */
  @Test
  public void testPortfolioValue() {
    clear("portfolios");
    model2 = new StockBetterModel();
    model2.createPortfolio("TechStocks");
    model2.addStockToPortfolioFile("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    model2.addStockToPortfolioFile("TechStocks", "GOOG", 5,
            LocalDate.of(2023, 6, 1));
    double portfolioValue = model2.portfolioValue("TechStocks",
            LocalDate.of(2023, 6, 1));
    assertEquals(2422.75, portfolioValue, 0.01);
    portfolioValue = model2.portfolioValue("TechStocks",
            LocalDate.of(2000, 6, 1));
    assertEquals(0, portfolioValue, 0.01);
  }


  /**
   * This method tests portfolio composition on better model.
   */
  @Test
  public void testPortfolioComposition() {
    clear("portfolios");
    betterModel2.createPortfolio("TechStocks");
    betterModel2.addStockToPortfolioFile("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    BarGraph composition = betterModel2.portfolioComposition("TechStocks",
            LocalDate.of(2023, 6, 1));
    String result = composition.interpret();
    assertTrue(betterModel2.portfolioComposition("TechStocks",
            LocalDate.of(2023, 6, 1)).interpret().contains("AAPL"));
    assertEquals("Composition of Portfolio 'TechStocks':\n" +
            "AAPL: 10.0 shares.", betterModel2.portfolioComposition("TechStocks",
            LocalDate.of(2023, 6, 1)).interpret());
    assertTrue(betterModel2.portfolioComposition("TechStocks",
            LocalDate.of(2023, 6, 1)).interpret().contains
            ("Composition of Portfolio 'TechStocks':"));
  }


  /**
   * This method tests portfolio distribution on better model.
   */
  @Test
  public void testPortfolioDistribution() {
    clear("portfolios");
    betterModel2.createPortfolio("TechStocks");
    betterModel2.addStockToPortfolioFile("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    BarGraph distribution = betterModel2.portfolioDistribution("TechStocks",
            LocalDate.of(2023, 6, 1));
    String result = distribution.interpret();
    assertTrue(result.contains("AAPL"));
  }


  @Test
  public void testPortfolioPerformance() {
    clear("portfolios");
    betterModel2.createPortfolio("TechStocks");
    betterModel2.addStockToPortfolioFile("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    BarGraph performance = betterModel2.portfolioPerformance("TechStocks",
            LocalDate.of(2023, 1, 1),
            LocalDate.of(2023, 6, 1), 5);
    String result = performance.interpret();
    assertTrue(result.contains("*"));
    assertTrue(result.contains("X-Axis Scale:"));
  }




  @Test
  public void testPortfolioRebalance() {
    clear("portfolios");
    betterModel2.createPortfolio("TechStocks");
    betterModel2.addStockToPortfolioFile("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    betterModel2.portfolioRebalance("TechStocks", List.of(50, 50),
            LocalDate.of(2023, 6, 1));
    BarGraph distribution = betterModel2.portfolioDistribution("TechStocks",
            LocalDate.of(2023, 6, 1));
    String result = distribution.interpret();
    assertTrue(result.contains("AAPL"));
  }




  @Test
  public void testIsValidSplit() {
    clear("portfolios");
    betterModel2.createPortfolio("TechStocks");
    betterModel2.addStockToPortfolioFile("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    boolean isValid = betterModel2.isValidSplit("TechStocks", List.of("AAPL", "GOOG"));
    assertFalse(isValid);
    betterModel2.addStockToPortfolioFile("TechStocks", "GOOG", 10,
            LocalDate.of(2023, 6, 1));
    isValid = betterModel2.isValidSplit("TechStocks", List.of("AAPL"));
    assertFalse(isValid);
  }


  /**
   * Tests addStockToPortfolio on StockBetterModel
   */
  @Test
  public void testAddStockToBetterPortfolio() {
    clear("portfolios");
    betterModel2 = new StockBetterModel();
    betterModel2.createPortfolio("TechStocks");
    betterModel2.addStockToPortfolioFile("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    double portfolioValue = betterModel2.portfolioValue("TechStocks", LocalDate.of
            (2023, 6, 2));
    // check for portfolio after add date
    assertEquals(1809.5, portfolioValue, 0.001);
    // check for portfolio before add date
    portfolioValue = betterModel2.portfolioValue("TechStocks",
            LocalDate.of(2023, 5, 2));
    assertEquals(0, portfolioValue, 0.001);
    // check for portfolio on add date
    portfolioValue = betterModel2.portfolioValue("TechStocks",
            LocalDate.of(2023, 6, 1));
    assertEquals(1800.9, portfolioValue, 0.001);
    // add multiple stocks so different stocks are in portfolio on differeent dates
    betterModel2.addStockToPortfolioFile("TechStocks", "GOOG", 15,
            LocalDate.of(2022, 6, 1));
    betterModel2.addStockToPortfolioFile("TechStocks", "GOOG", 15,
            LocalDate.of(2021, 2, 1));
    portfolioValue = betterModel2.portfolioValue("TechStocks",
            LocalDate.of(2022, 6, 1));
    assertEquals(68482.2, portfolioValue, 0.001);
    portfolioValue = betterModel2.portfolioValue("TechStocks",
            LocalDate.of(2021, 6, 1));
    assertEquals(72894.3, portfolioValue, 0.001);
    portfolioValue = betterModel2.portfolioValue("TechStocks",
            LocalDate.of(2024, 6, 1));
    assertEquals(7141.3, portfolioValue, 0.001);
    portfolioValue = betterModel2.portfolioValue("TechStocks",
            LocalDate.of(2000, 6, 1));
    assertEquals(0, portfolioValue, 0.001);
  }


  /**
   * Test remove stock from better portfolio.
   */
  @Test
  public void testRemoveStockFromBetterPortfolio() {
    clear("portfolios");
    betterModel2.createPortfolio("TechStocks");
    betterModel2.addStockToPortfolioFile("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    betterModel2.removeStockFromPortfolioFile("TechStocks", "AAPL", 5,
            LocalDate.of(2023, 7, 2));
    double portfolioValue = betterModel2.portfolioValue("TechStocks",
            LocalDate.of(2023, 6, 1));
    assertEquals(900.45, portfolioValue, 0.001);
    betterModel2.removeStockFromPortfolioFile("TechStocks", "AAPL", 5,
            LocalDate.of(2023, 6, 3));
    portfolioValue = betterModel2.portfolioValue("TechStocks",
            LocalDate.of(2023, 6, 3));
    assertEquals(0, portfolioValue, 0.01);
  }


}
