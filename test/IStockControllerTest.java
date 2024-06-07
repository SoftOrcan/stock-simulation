import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import controller.TextualStockController;
import model.IStockModel;

import static org.junit.Assert.*;

/**
 * Tests and examples for IStockController interface.
 */
public class IStockControllerTest {
  private TextualStockController controller;
  private MockStockView mockView;

  @Before
  public void setUp() {
    IStockModel mockModel = new MockStockModel();
    mockView = new MockStockView();
    controller = new TextualStockController(mockModel, mockView);
  }

  @Test
  public void testControl_StockExaminePeriod() {
    // Set up input.
    String input = "stock-examine-period AAPL 2023-01-01 2023-06-01 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Gain/Loss of AAPL: 100.0.", mockView.getLastDisplayedMessage());
  }

  @Test
  public void testControl_StockExamineAverage() {
    // Set up input.
    String input = "stock-examine-average AAPL 2023-01-01 30 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Moving average of AAPL: 50.0.", mockView.getLastDisplayedMessage());
  }

  @Test
  public void testControl_StockExamineCrossovers() {
    // Set up input.
    String input = "stock-examine-crossovers AAPL 2023-01-01 2023-06-01 20 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Crossovers of AAPL: [2024-06-06, 2024-06-05].", mockView.getLastDisplayedMessage());
  }

  @Test
  public void testControl_PortfolioCreate() {
    // Set up input.
    String input = "portfolio-create MyPortfolio quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Created portfolio MyPortfolio.", mockView.getLastDisplayedMessage());
  }

  @Test
  public void testControl_PortfolioAdd() {
    // Set up input.
    String input = "portfolio-add MyPortfolio AAPL 10 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Added 10 of AAPL to portfolio MyPortfolio.", mockView.getLastDisplayedMessage());
  }

  @Test
  public void testControl_PortfolioRemove() {
    // Set up input.
    String input = "portfolio-remove MyPortfolio AAPL quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Removed AAPL from portfolio MyPortfolio.", mockView.getLastDisplayedMessage());
  }

  @Test
  public void testControl_PortfolioValue() {
    // Set up input.
    String input = "portfolio-value MyPortfolio 2023-06-06 quit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    // Run control method.
    controller.control();

    // Check if the correct message is displayed.
    assertEquals("Value of MyPortfolio: 500.0.", mockView.getLastDisplayedMessage());
  }
}