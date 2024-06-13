import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import controller.TextualStockController;

import static org.junit.Assert.assertEquals;

/**
 * Tests and examples for TextualStockController class.
 */
public class TextualStockControllerTest {
  private TextualStockController controller;
  private MockStockView mockView;
  private MockStockModel mockModel;

  @Before
  public void setUp() {
    mockModel = new MockStockModel();
    mockView = new MockStockView();
  }

  private <T> T last(List<T> strings) {
    return strings.get(strings.size() - 1);
  }

  private <T> T secondLast(List<T> strings) {
    return strings.get(strings.size() - 2);
  }

  private void setInput(String input) {
    controller = new TextualStockController(mockModel, mockView, new StringReader(input));
  }

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

  @Test
  public void testControllerStockExaminePeriodInvalid() {
    // Set up input.
    setInput("1 AZAZAZ AAPL 2023 01 01 2023 06 01 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerStockExamineAverageInvalid() {
    // Set up input.
    setInput("2 AZAZAZ AAPL 2023 01 01 30 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerStockExamineCrossoversInvalid() {
    // Set up input.
    setInput("3 AZAZAZ AAPL 2023 01 01 2023 06 01 20 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioAddToInvalid() {
    // Set up input.
    setInput("4 MyPortfolio 5 AZAZAZ MyPortfolio AAPL 10 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Portfolio 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioRemoveToInvalid() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL 10 6 AZAZAZ MyPortfolio AAPL 5 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Portfolio 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioValueOfInvalid() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL 10 7 AZAZAZ MyPortfolio 2023 06 06 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Portfolio 'AZAZAZ' does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioAddFractional() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL 10.5 6 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("The numerical value 10.5 is invalid.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioAddNegative() {
    // Set up input.
    setInput("4 MyPortfolio 5 MyPortfolio AAPL -10 5 q");

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("The numerical value -10 is invalid.", last(mockView.getErrors()));
  }

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