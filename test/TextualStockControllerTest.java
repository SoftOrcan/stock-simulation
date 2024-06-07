import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import controller.TextualStockController;
import model.IStockModel;

import static org.junit.Assert.*;

/**
 * Tests and examples for TextualStockController class.
 */
public class TextualStockControllerTest {
  private TextualStockController controller;
  private MockStockView mockView;

  @Before
  public void setUp() {
    IStockModel mockModel = new MockStockModel();
    mockView = new MockStockView();
    controller = new TextualStockController(mockModel, mockView);
  }

  private <T> T last(List<T> strings) {
    return strings.get(strings.size() - 1);
  }

  @Test
  public void testControllerStockExaminePeriod() {
    // Set up input.
    String input = "stock-examine-period AAPL 2023-01-01 2023-06-01 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Gain/Loss of AAPL: 0.0.", last(mockView.getMessages()));
  }

  @Test
  public void testControllerStockExamineAverage() {
    // Set up input.
    String input = "stock-examine-average AAPL 2023-01-01 30 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Moving average of AAPL: 0.0.", last(mockView.getMessages()));
  }

  @Test
  public void testControllerStockExamineCrossovers() {
    // Set up input.
    String input = "stock-examine-crossovers AAPL 2023-01-01 2023-06-01 20 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Crossovers of AAPL: [].", last(mockView.getMessages()));
  }

  @Test
  public void testControllerPortfolioCreate() {
    // Set up input.
    String input = "portfolio-create MyPortfolio quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Created portfolio MyPortfolio.", last(mockView.getMessages()));
  }

  @Test
  public void testControllerPortfolioAdd() {
    // Set up input.
    String input = "portfolio-add MyPortfolio AAPL 10 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Added 10 of AAPL to portfolio MyPortfolio.",
            last(mockView.getMessages()));
  }

  @Test
  public void testControllerPortfolioRemove() {
    // Set up input.
    String input = "portfolio-remove MyPortfolio AAPL quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Removed AAPL from portfolio MyPortfolio.", last(mockView.getMessages()));
  }

  @Test
  public void testControllerPortfolioValue() {
    // Set up input.
    String input = "portfolio-value MyPortfolio 2023-06-06 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Value of MyPortfolio: 0.0.", last(mockView.getMessages()));
  }

  @Test
  public void testControllerStockExaminePeriodInvalid() {
    // Set up input.
    String input = "stock-examine-period AZAZAZ 2023-01-01 2023-06-01 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol AZAZAZ does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerStockExamineAverageInvalid() {
    // Set up input.
    String input = "stock-examine-average AZAZAZ 2023-01-01 30 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol AZAZAZ does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerStockExamineCrossoversInvalid() {
    // Set up input.
    String input = "stock-examine-crossovers AZAZAZ 2023-01-01 2023-06-01 20 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol AZAZAZ does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioAddToInvalid() {
    // Set up input.
    String input = "portfolio-add AZAZAZ AAPL 10 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Portfolio AZAZAZ does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioRemoveToInvalid() {
    // Set up input.
    String input = "portfolio-remove AZAZAZ AAPL quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Portfolio AZAZAZ does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioValueOfInvalid() {
    // Set up input.
    String input = "portfolio-value AZAZAZ 2023-06-06 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Portfolio AZAZAZ does not exist.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioAddFractional() {
    // Set up input.
    String input = "portfolio-add MyPortfolio AAPL 10.5 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Must use integer for amount.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioAddNegative() {
    // Set up input.
    String input = "portfolio-add MyPortfolio AAPL -10 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Must use positive amount.", last(mockView.getErrors()));
  }

  @Test
  public void testControllerPortfolioAddInvalid() {
    // Set up input.
    String input = "portfolio-add MyPortfolio AZAZAZ 10 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Stock symbol AZAZAZ does not exist.", last(mockView.getErrors()));
  }
}