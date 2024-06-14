import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

import controller.TextualStockBetterController;
import controller.TextualStockController;
import model.BetterMockStockModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests and examples for TextualStockController class.
 */
public class IStockControllerTest {
  private TextualStockController controller;
  private MockStockView mockView;
  private MockStockModel mockModel;

  private TextualStockBetterController betterController;
  private BetterMockStockView betterMockView;
  private BetterMockStockModel betterMockModel;

  /**
   * Sets up mock models and views before each test.
   * Creates necessary data directories if they don't exist.
   */
  @Before
  public void setUp() {
    File dataDirectory = new File("./data");
    if (!dataDirectory.exists()) {
      dataDirectory.mkdirs();
    }

    mockModel = new MockStockModel();
    mockView = new MockStockView();

    betterMockModel = new BetterMockStockModel();
    betterMockView = new BetterMockStockView();
  }

  private <T> T last(List<T> strings) {
    return strings.get(strings.size() - 1);
  }

  private <T> T secondLast(List<T> strings) {
    return strings.get(strings.size() - 2);
  }

  /**
   * Sets up input for textual controllers.
   *
   * @param input The input string to simulate user input.
   */
  private void setInput(String input) {
    controller = new TextualStockController(mockModel, mockView, new StringReader(input));
    betterController =
        new TextualStockBetterController(betterMockModel, betterMockView, new StringReader(input));
  }

  /**
   * Tests portfolio creation in {@link TextualStockBetterController}.
   */
  @Test
  public void testBetterCreatePortfolio() {
    String input = "5\nTechStocks\nq\n";
    setInput(input);

    betterController.control();
    betterMockModel.createPortfolio("TechStocks");

    assertTrue(betterMockModel.hasPortfolio("TechStocks"));
    assertTrue(betterMockView.getMessages().contains("Created portfolio 'TechStocks'."));
  }

  /**
   * Tests handling of undefined initial input in {@link TextualStockBetterController}.
   */
  @Test
  public void testWrongInitialInput() {
    String input = "20\n";
    setInput(input);
    betterController.control();
    assertTrue(betterMockView.getMessages().contains("Undefined instruction: 20."));
  }

  /**
   * Tests adding a stock to a portfolio in {@link TextualStockBetterController}.
   */
  @Test
  public void testBetterAddStockToPortfolio() {
    String input = "5\nTechStocks\n6\nTechStocks\nAAPL\n10\n2023\n06\n01\nq\n";
    setInput(input);

    betterController.control();
    // Verify the stock was added to the portfolio.
    betterMockModel.addStockToPortfolio("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    assertTrue(betterMockView.getMessages().contains("On 2023-06-01: Added 'AAPL' " +
            "(quantity: 10) to portfolio 'TechStocks'."));
  }

  /**
   * Tests removing a stock from a portfolio in {@link TextualStockBetterController}.
   */
  @Test
  public void testBetterRemoveStockFromPortfolio() {
    String input = "5\nTechStocks\n6\nTechStocks\nAAPL\n10\n2023\n06\n01\n7\n" +
            "TechStocks\nAAPL\n5\n2023\n06\n01\nq\n";
    setInput(input);

    betterController.control();

    // Verify the stock was added to the portfolio
    betterMockModel.addStockToPortfolio("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    // Verify the stock was removed from the portfolio
    betterMockModel.removeStockFromPortfolio("TechStocks", "AAPL", 5,
            LocalDate.of(2023, 6, 1));
    assertTrue(betterMockView.getMessages().contains("On 2023-06-01: Removed 'AAPL' " +
            "(quantity: 5) from portfolio 'TechStocks'."));
  }

  /**
   * Tests removing a stock from a portfolio.
   */
  @Test (expected = NullPointerException.class)
  public void testBetterRemoveStockFromPortfolioStockNotInPortfolio() {
    String input = "5\nTechStocks\n6\nTechStocks\nAAPL\n10\n2023\n06\n01\n7\n" +
            "TechStocks\nSWK\n5\n2023\n06\n01\nq\n";
    setInput(input);

    betterController.control();

    // Verify the stock was added to the portfolio
    betterMockModel.addStockToPortfolio("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    betterMockModel.removeStockFromPortfolio("TechStocks", "SWK", 5,
            LocalDate.of(2023, 6, 1));
    assertTrue(betterMockView.getMessages().contains("On 2023-06-01: Removed 'SWK' " +
            "(quantity: 5) from portfolio 'TechStocks'."));
  }

  /**
   * Tests stock change method on better stock model.
   */
  @Test
  public void testBetterStockChange() {
    String input = "1\nAAPL\n2023\n01\n01\n2023\n12\n31\nq\n";
    setInput(input);

    betterController.control();

    assertTrue(betterMockView.getMessages().contains("Gain/Loss of 'AAPL': 100.0."));
  }

  /**
   * Tests stockmovingaverage method on better stock model.
   */
  @Test
  public void testBetterStockMovingAverage() {
    String input = "2\nAAPL\n2023\n01\n01\n30\nq\n";
    setInput(input);

    betterController.control();

    assertTrue(betterMockView.getMessages().contains("Moving average of 'AAPL': 150.0."));
  }

  /**
   * Tests stockmovingaverage method on better stock model with invalid input.
   */
  @Test (expected = NoSuchElementException.class)
  public void testBetterStockMovingAverageInvalidDate() {
    String input = "2\nAAPL\n2023\n90\n01\n30\nq\n";
    setInput(input);

    betterController.control();

    assertTrue(betterMockView.getMessages().contains("Moving average of 'AAPL': 150.0."));
  }

  /**
   * Tests stock crossovers method on better model.
   */
  @Test
  public void testBetterStockCrossovers() {
    String input = "3\nAAPL\n2023\n01\n01\n2023\n12\n31\n30\nq\n";
    setInput(input);

    betterController.control();

    assertTrue(
        betterMockView.getMessages().contains("Crossovers of 'AAPL': [2023-01-01, 2023-12-31]."));
  }

  /**
   * Tests stock crossovers method on better model with invalid input.
   */
  @Test (expected = NoSuchElementException.class)
  public void testBetterStockCrossoversInvalidDate() {
    String input = "3\nAAPL\n2021\n91\n01\n2023\n12\n31\n30\nq\n";
    setInput(input);

    betterController.control();

    assertTrue(
            betterMockView.getMessages().contains("Crossovers of 'AAPL': " +
                    "[2023-01-01, 2023-12-31]."));
  }

  /**
   * Tests portfolio value method on better model.
   */
  @Test
  public void testBetterPortfolioValue() {
    String input = "5\nTechStocks\n10\nTechStocks\n2023\n01\n01\nq\n";
    setInput(input);

    betterController.control();

    assertTrue(betterMockView.getMessages().contains("Value of TechStocks: $1000.0."));
  }

  /**
   * Tests portfolio value method on better model with invalid input.
   */
  @Test (expected = NoSuchElementException.class)
  public void testBetterPortfolioValueNotExist() {
    String input = "5\nTechStocks\n10\npoopyPortfolio\n";
    setInput(input);

    betterController.control();
    System.out.println(betterMockView.getMessages());
    assertTrue(betterMockView.getMessages().contains("Value of TechStocks: $1000.0."));
  }

  /**
   * Tests better portfolio composition method.
   */
  @Test
  public void testBetterPortfolioComposition() {
    String input = "5\nTechStocks\n8\nTechStocks\n2023\n01\n01\nq\n";
    setInput(input);

    betterController.control();

    assertTrue(betterMockView.getMessages().contains("Composition:"));
  }

  /**
   * Tests better portfolio distribution method.
   */
  @Test
  public void testBetterPortfolioDistribution() {
    String input = "5\nTechStocks\n9\nTechStocks\n2023\n01\n01\nq\n";
    setInput(input);

    betterController.control();

    assertTrue(betterMockView.getMessages().contains("Distribution:"));
  }

  /**
   * Tests better portfolio rebalance method.
   */
  @Test
  public void testBetterPortfolioRebalance() {
    String inputCreatePortfolio = "5\nportfolio\nq\n";
    setInput(inputCreatePortfolio);
    betterController.control();
    String inputAddStock = "6\nportfolio\nAAPL\n20\n2024\n05\n02\nq\n";
    setInput(inputAddStock);
    betterController.control();
    String inputAddAnotherStock = "6\nportfolio\nGOOG\n20\n2024\n05\n02\nq\n";
    setInput(inputAddAnotherStock);
    betterController.control();
    String inputRebalance = "11\nportfolio\n50-50\n2024\n05\n02\nq\n";
    setInput(inputRebalance);
    betterController.control();
    assertTrue(betterMockView.getMessages().get(betterMockView.getMessages().size() - 2).
            contains("Rebalanced portfolio 'portfolio'"));
  }

  /**
   * Tests better portfolio rebalance method with invalid input.
   */
  @Test (expected = NoSuchElementException.class)
  public void testBetterPortfolioRebalanceInvalidRatio() {
    String inputCreatePortfolio = "5\nName\nq\n";
    setInput(inputCreatePortfolio);
    betterController.control();
    String inputAddStock = "6\nName\nAAPL\n20\n2024\n05\n02\nq\n";
    setInput(inputAddStock);
    betterController.control();
    String inputAddAnotherStock = "6\nName\nGOOG\n20\n2024\n05\n02\nq\n";
    setInput(inputAddAnotherStock);
    betterController.control();
    String inputRebalance = "11\nName\n50-50-50-50\n2024\n05\n02\nq\n";
    setInput(inputRebalance);
    betterController.control();
    assertTrue(betterMockView.getMessages().get(betterMockView.getMessages().size() - 2).
            contains("Rebalanced portfolio 'portfolio'"));
  }

  /**
   * Tests better portfolio rebalance method with invalid input.
   */
  @Test (expected = NoSuchElementException.class)
  public void testBetterPortfolioRebalanceInvalidTicker() {
    String inputCreatePortfolio = "5\nName\nq\n";
    setInput(inputCreatePortfolio);
    betterController.control();
    String inputAddStock = "6\nName\nAPL\n20\n2024\n05\n02\nq\n";
    setInput(inputAddStock);
    betterController.control();
    String inputAddAnotherStock = "6\nName\nGOOG\n20\n2024\n05\n02\nq\n";
    setInput(inputAddAnotherStock);
    betterController.control();
    String inputRebalance = "11\nName\n50-50-50-50\n2024\n05\n02\nq\n";
    setInput(inputRebalance);
    betterController.control();
    assertTrue(betterMockView.getMessages().get(betterMockView.getMessages().size() - 2).
            contains("Rebalanced portfolio 'portfolio'"));
  }

  /**
   * Tests better portfolio rebalance method with invalid input.
   */
  @Test (expected = NoSuchElementException.class)
  public void testBetterPortfolioRebalanceInvalidDate() {
    String inputCreatePortfolio = "5\nName\nq\n";
    setInput(inputCreatePortfolio);
    betterController.control();
    String inputAddStock = "6\nName\nAPL\n20\n2024\n35\n02\nq\n";
    setInput(inputAddStock);
    betterController.control();
    String inputAddAnotherStock = "6\nName\nGOOG\n20\n2024\n05\n02\nq\n";
    setInput(inputAddAnotherStock);
    betterController.control();
    String inputRebalance = "11\nName\n50-50-50-50\n2024\n05\n02\nq\n";
    setInput(inputRebalance);
    betterController.control();
    assertTrue(betterMockView.getMessages().get(betterMockView.getMessages().size() - 2).
            contains("Rebalanced portfolio 'portfolio'"));
  }

  /**
   * Tests better portfolio performance method.
   */
  @Test
  public void testBetterPortfolioPerformance() {
    String input = "5\nTechStocks\n12\nTechStocks\n2023\n01\n01\n2023\n12\n31\n10\nq\n";
    setInput(input);

    betterController.control();

    assertTrue(secondLast(betterMockView.getMessages()).contains("Performance of 'TechStocks':"));
  }

  /**
   * Tests when invalid entered.
   */
  @Test (expected = NoSuchElementException.class)
  public void testBetterInvalidSymbol() {
    String input = "1\nINVALID\nq\n";
    setInput(input);

    betterController.control();

    assertTrue(betterMockView.getErrors().contains("Stock symbol 'INVALID' does not exist."));
  }

  /**
   * Tests with invalid date entered.
   */
  @Test (expected = DateTimeParseException.class)
  public void testBetterInvalidDate() {
    String input = "1\nAAPL\n2023\n02\n30\n2023\n12\n31\nq\n";
    setInput(input);

    betterController.control();
    assertTrue(betterMockView.getErrors().contains("The day is invalid."));
  }

  /**
   * Tests with invalid symbol.
   */
  @Test (expected = NoSuchElementException.class)
  public void testBetterInvalidSymbol2() {
    String input = "2\nINVALID\nq\n";
    setInput(input);

    betterController.control();
    assertTrue(betterMockView.getErrors().contains("Stock symbol 'INVALID' does not exist."));
  }

  /**
   * Tests with invalid date.
   */
  @Test (expected = DateTimeParseException.class)
  public void testBetterInvalidDate2() {
    String input = "2\nAAPL\n2023\n02\n30\n2023\n12\n31\nq\n";
    setInput(input);

    betterController.control();
    assertTrue(betterMockView.getErrors().contains("The day is invalid."));
  }

  /**
   * Tests add stock to portfolio.
   */
  @Test (expected = NoSuchElementException.class)
  public void testBetterAddStockToPortfolioInvalidDate() {
    String input = "5\nTechStocks\n6\nTechStocks\nAAPL\n20\n2023\n50\n01\nq\n";
    setInput(input);

    betterController.control();

    // Verify the stock was added to the portfolio
    betterMockModel.addStockToPortfolio("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    assertTrue(betterMockView.getMessages().contains("On 2023-06-01: Added 'AAPL' " +
            "(quantity: 10) to portfolio 'TechStocks'."));
  }

  /**
   * Tests remove from portfolio invalid.
   */
  @Test (expected = NullPointerException.class)
  public void testBetterRemoveStockFromPortfolioInvalidSymbol() {
    String input = "5\nTechStocks\n6\nTechStocks\nAAPL\n10\n2023\n06\n01\n7\n" +
            "TechStocks\nPOOP\n5\n2023\n06\n01\nq\n";
    setInput(input);

    betterController.control();
  }

  /**
   * Tests remove from portfolio invalid.
   */
  @Test (expected = NoSuchElementException.class)
  public void testBetterRemoveStockFromPortfolioInvalidDate() {
    String input = "5\nTechStocks\n6\nTechStocks\nAAPL\n10\n2023\n06\n01\n7\n" +
            "TechStocks\nAAPL\n5\n2024\n06\n91\nq\n";
    setInput(input);

    betterController.control();

    // Verify the stock was added to the portfolio
    betterMockModel.addStockToPortfolio("TechStocks", "AAPL", 10,
            LocalDate.of(2023, 6, 1));
    // Verify the stock was removed from the portfolio
    betterMockModel.removeStockFromPortfolio("TechStocks", "AAPL", 5,
            LocalDate.of(2023, 6, 1));
    assertTrue(betterMockView.getMessages().contains("On 2023-06-01: Removed 'AAPL' " +
            "(quantity: 5) from portfolio 'TechStocks'."));
  }

  /**
   * Tests examine period.
   */
  @Test
  public void testControllerStockExaminePeriod() {
    // Set up input.
    setInput("1 AAPL 2023 01 01 2023 06 01 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Gain/Loss of 'AAPL': 0.0.", secondLast(mockView.getMessages()));
    assertEquals("calculateChange: AAPL, 2023-01-01, 2023-06-01", last(mockModel.getLog()));
  }

  /**
   * Tests examining stock change period in {@link TextualStockController}.
   */
  @Test
  public void testControllerStockExamineAverage() {
    // Set up input.
    setInput("2 AAPL 2023 01 01 30 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Moving average of 'AAPL': 0.0.", secondLast(mockView.getMessages()));
    assertEquals("movingAverage: AAPL, 2023-01-01, 30", last(mockModel.getLog()));
  }

  /**
   * Tests examining stock moving average in {@link TextualStockController}.
   */
  @Test
  public void testControllerStockExamineCrossovers() {
    // Set up input.
    setInput("3 AAPL 2023 01 01 2023 06 01 20 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Crossovers of 'AAPL': [].", secondLast(mockView.getMessages()));
    assertEquals("findCrossovers: AAPL, 2023-01-01, 2023-06-01, 20", last(mockModel.getLog()));
  }

  /**
   * Tests examining stock crossovers in {@link TextualStockController}.
   */
  @Test
  public void testControllerPortfolioCreate() {
    // Set up input.
    setInput("4 MyPortfolio q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Created portfolio 'MyPortfolio'.", secondLast(mockView.getMessages()));
    assertEquals("createPortfolio: MyPortfolio", last(mockModel.getLog()));
  }

  /**
   * Tests creating a portfolio in {@link TextualStockController}.
   */
  @Test
  public void testControllerPortfolioAdd() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL 10 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Added 'AAPL' (quantity: 10) to portfolio 'MyPortfolio'.",
        secondLast(mockView.getMessages()));
    assertEquals("addStockToPortfolio: MyPortfolio, AAPL, 10.000000", last(mockModel.getLog()));
  }

  /**
   * Tests removing a stock to a portfolio in {@link TextualStockController}.
   */
  @Test
  public void testControllerPortfolioRemove() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL 10 6 MyPortfolio AAPL 5 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Removed 'AAPL' (quantity: 5) from portfolio 'MyPortfolio'.",
        secondLast(mockView.getMessages()));
    assertEquals("removeStockFromPortfolio: MyPortfolio, AAPL, 5", last(mockModel.getLog()));
  }

  /**
   * Tests getting portfolio value.
   */
  @Test
  public void testControllerPortfolioValue() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL 10 7 MyPortfolio 2023 06 06 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Value of MyPortfolio: $0.0.", secondLast(mockView.getMessages()));
    assertEquals("valueOfPortfolio: MyPortfolio, 2023-06-06", last(mockModel.getLog()));
  }

  /**
   * Examine when period invalid.
   */
  @Test
  public void testControllerStockExaminePeriodInvalid() {
    // Set up input.
    setInput("1 AZAZAZ AAPL 2023 01 01 2023 06 01 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  /**
   * Test examine average with invalid input.
   */
  @Test
  public void testControllerStockExamineAverageInvalid() {
    // Set up input.
    setInput("2 AZAZAZ AAPL 2023 01 01 30 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  /**
   * Test with invalid input with crossovers.
   */
  @Test
  public void testControllerStockExamineCrossoversInvalid() {
    // Set up input.
    setInput("3 AZAZAZ AAPL 2023 01 01 2023 06 01 20 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  /**
   * Test add with invalid.
   */
  @Test
  public void testControllerPortfolioAddToInvalid() {
    // Set up input.
    setInput("4 MyPortfolio 5 AZAZAZ MyPortfolio AAPL 10 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Portfolio 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  /**
   * Test remove with invalid.
   */
  @Test
  public void testControllerPortfolioRemoveToInvalid() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL 10 6 AZAZAZ MyPortfolio AAPL 5 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Portfolio 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  /**
   * Test value with invalid.
   */
  @Test
  public void testControllerPortfolioValueOfInvalid() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL 10 7 AZAZAZ MyPortfolio 2023 06 06 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Portfolio 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  /**
   * Test add with fraction.
   */
  @Test
  public void testControllerPortfolioAddFractional() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL 10.5 6 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("The numerical value 10.5 is invalid.", last(mockView.getErrors()));
  }

  /**
   * Test add with negative.
   */
  @Test
  public void testControllerPortfolioAddNegative() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL -10 5 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("The numerical value -10 is invalid.", last(mockView.getErrors()));
  }

  /**
   * Test with add invalid input.
   */
  @Test
  public void testControllerPortfolioAddInvalid() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AZAZAZ AAPL 10 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }
}