import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.BetterMockStockModel;
import model.IStockBetterModel;
import view.MockGraphicalStockView;
import controller.GraphicalStockController;

import java.util.List;
import java.util.Map;


import java.util.HashMap;
import view.IStockView;

/**
 * This class tests the methods of GraphicalStockController.
 */
public class GraphicalStockControllerTest {

  private IStockView view;
  private GraphicalStockController controller;

  @Before
  public void setUp() {
    IStockBetterModel model = new BetterMockStockModel();
    view = new MockGraphicalStockView();
    controller = new GraphicalStockController(model, view);
    view.setFeatures(controller);
    controller.control();
  }

  @Test
  public void testControl() {
    controller.control();
    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(4, messages.size());
    assertEquals("setPanel(ActionSelection)", messages.get(0));
    assertEquals("draw()", messages.get(1));
    assertEquals("setPanel(ActionSelection)", messages.get(2));
    assertEquals("draw()", messages.get(3));
  }

  @Test
  public void testCreatePortfolio() {
    // Simulate user actions for creating a portfolio
    controller.enter("create");
    Map<String, String> entries = new HashMap<>();
    entries.put("name", "TestPortfolio");
    controller.submit("create", entries);

    // Verify the expected messages
    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(4, messages.size());
    assertEquals("setPanel(ActionSelection)", messages.get(0));
    assertEquals("draw()", messages.get(1));
    assertEquals("setPanel(create)", messages.get(2));
    assertEquals("setPanel(Display)", messages.get(3));
  }

  @Test
  public void testAddStock() {
    // First, create a portfolio
    controller.enter("create");
    Map<String, String> createEntries = new HashMap<>();
    createEntries.put("name", "TestPortfolio");
    controller.submit("create", createEntries);

    // Then, add stock to the created portfolio
    controller.enter("add");
    Map<String, String> addEntries = new HashMap<>();
    addEntries.put("symbol", "AAPL");
    addEntries.put("shares", "10");
    addEntries.put("day", "1");
    addEntries.put("month", "1");
    addEntries.put("year", "2022");
    controller.submit("add", addEntries);

    // Verify the expected messages
    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(6, messages.size());
    assertEquals("setPanel(ActionSelection)", messages.get(0));
    assertEquals("draw()", messages.get(1));
    assertEquals("setPanel(create)", messages.get(2));
    assertEquals("setPanel(Display)", messages.get(3));
    assertEquals("setPanel(add)", messages.get(4));
    assertEquals("setPanel(Display)", messages.get(5));
  }

  @Test
  public void testRemoveStock() {
    // First, create a portfolio
    controller.enter("create");
    Map<String, String> createEntries = new HashMap<>();
    createEntries.put("name", "TestPortfolio");
    controller.submit("create", createEntries);

    // Add stock to the created portfolio
    controller.enter("add");
    Map<String, String> addEntries = new HashMap<>();
    addEntries.put("symbol", "AAPL");
    addEntries.put("shares", "10");
    addEntries.put("day", "1");
    addEntries.put("month", "1");
    addEntries.put("year", "2022");
    controller.submit("add", addEntries);

    // Remove stock from the created portfolio
    controller.enter("remove");
    Map<String, String> removeEntries = new HashMap<>();
    removeEntries.put("symbol", "AAPL");
    removeEntries.put("shares", "5");
    removeEntries.put("day", "1");
    removeEntries.put("month", "1");
    removeEntries.put("year", "2022");
    controller.submit("remove", removeEntries);

    // Verify the expected messages
    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(8, messages.size());
    assertEquals("setPanel(ActionSelection)", messages.get(0));
    assertEquals("draw()", messages.get(1));
    assertEquals("setPanel(create)", messages.get(2));
    assertEquals("setPanel(Display)", messages.get(3));
    assertEquals("setPanel(add)", messages.get(4));
    assertEquals("setPanel(Display)", messages.get(5));
    assertEquals("setPanel(remove)", messages.get(6));
    assertEquals("setPanel(Display)", messages.get(7));
  }

  @Test
  public void testPortfolioValue() {
    // First, create a portfolio
    controller.enter("create");
    Map<String, String> createEntries = new HashMap<>();
    createEntries.put("name", "TestPortfolio");
    controller.submit("create", createEntries);

    // Add stock to the created portfolio
    controller.enter("add");
    Map<String, String> addEntries = new HashMap<>();
    addEntries.put("symbol", "AAPL");
    addEntries.put("shares", "10");
    addEntries.put("day", "1");
    addEntries.put("month", "1");
    addEntries.put("year", "2022");
    controller.submit("add", addEntries);

    // Check the value of the portfolio
    controller.enter("value");
    Map<String, String> valueEntries = new HashMap<>();
    valueEntries.put("day", "1");
    valueEntries.put("month", "1");
    valueEntries.put("year", "2022");
    controller.submit("value", valueEntries);

    // Verify the expected messages
    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(8, messages.size());
    assertEquals("setPanel(ActionSelection)", messages.get(0));
    assertEquals("draw()", messages.get(1));
    assertEquals("setPanel(create)", messages.get(2));
    assertEquals("setPanel(Display)", messages.get(3));
    assertEquals("setPanel(add)", messages.get(4));
    assertEquals("setPanel(Display)", messages.get(5));
    assertEquals("setPanel(value)", messages.get(6));
    assertEquals("setPanel(Display)", messages.get(7));
  }

  @Test
  public void testStockValue() {
    // Check the value of the portfolio
    controller.enter("value");
    Map<String, String> valueEntries = new HashMap<>();
    valueEntries.put("day", "1");
    valueEntries.put("month", "1");
    valueEntries.put("year", "2022");
    controller.submit("value", valueEntries);
    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals("setPanel(Display)", messages.get(3));
  }

  @Test
  public void testPortfolioComposition() {
    // First, create a portfolio
    controller.enter("create");
    Map<String, String> createEntries = new HashMap<>();
    createEntries.put("name", "TestPortfolio");
    controller.submit("create", createEntries);

    // Add stock to the created portfolio
    controller.enter("add");
    Map<String, String> addEntries = new HashMap<>();
    addEntries.put("symbol", "AAPL");
    addEntries.put("shares", "10");
    addEntries.put("day", "1");
    addEntries.put("month", "1");
    addEntries.put("year", "2022");
    controller.submit("add", addEntries);

    // Check the composition of the portfolio
    controller.enter("composition");
    Map<String, String> compositionEntries = new HashMap<>();
    compositionEntries.put("day", "1");
    compositionEntries.put("month", "1");
    compositionEntries.put("year", "2022");
    controller.submit("composition", compositionEntries);

    // Verify the expected messages
    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(8, messages.size());
    assertEquals("setPanel(ActionSelection)", messages.get(0));
    assertEquals("draw()", messages.get(1));
    assertEquals("setPanel(create)", messages.get(2));
    assertEquals("setPanel(Display)", messages.get(3));
    assertEquals("setPanel(add)", messages.get(4));
    assertEquals("setPanel(Display)", messages.get(5));
    assertEquals("setPanel(composition)", messages.get(6));
    assertEquals("setPanel(Display)", messages.get(7));

  }

  @Test
  public void testPortfolioCompositionInvalidInput() {
    controller.enter("create");
    Map<String, String> createEntries = new HashMap<>();
    createEntries.put("name", "TestPortfolio");
    controller.submit("create", createEntries);

    controller.enter("add");
    Map<String, String> addEntries = new HashMap<>();
    addEntries.put("symbol", "AAPL");
    addEntries.put("shares", "10");
    addEntries.put("day", "1");
    addEntries.put("month", "1");
    addEntries.put("year", "2022");
    controller.submit("add", addEntries);

    controller.enter("composition");
    Map<String, String> compositionEntries = new HashMap<>();
    compositionEntries.put("day", "32");
    compositionEntries.put("month", "13");
    compositionEntries.put("year", "1999");
    controller.submit("composition", compositionEntries);

    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(8, messages.size());
    assertEquals("setPanel(ActionSelection)", messages.get(0));
    assertEquals("draw()", messages.get(1));
    assertEquals("setPanel(create)", messages.get(2));
    assertEquals("setPanel(Display)", messages.get(3));
    assertEquals("setPanel(add)", messages.get(4));
    assertEquals("setPanel(Display)", messages.get(5));
    assertEquals("setPanel(composition)", messages.get(6));
    assertEquals("displayError(Invalid input provided.)", messages.get(7));
  }

  @Test
  public void testSavePortfolio() {
    // First session
    controller.control();
    controller.enter("create");
    Map<String, String> createEntries = new HashMap<>();
    createEntries.put("name", "TestPortfolio");
    controller.submit("create", createEntries);

    controller.enter("add");
    Map<String, String> addEntries = new HashMap<>();
    addEntries.put("symbol", "AAPL");
    addEntries.put("shares", "10");
    addEntries.put("day", "1");
    addEntries.put("month", "1");
    addEntries.put("year", "2022");
    controller.submit("add", addEntries);

    controller.enter("value");
    Map<String, String> valueEntries = new HashMap<>();
    valueEntries.put("day", "1");
    valueEntries.put("month", "1");
    valueEntries.put("year", "2022");
    controller.submit("value", valueEntries);

    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(10, messages.size());

    // Simulate quitting the program
    setUp(); // Reinitialize the model and view (simulate restart)

    // Second session
    controller.control();

    // Check the saved portfolio value
    controller.enter("value");
    Map<String, String> newValueEntries = new HashMap<>();
    newValueEntries.put("day", "1");
    newValueEntries.put("month", "1");
    newValueEntries.put("year", "2022");
    controller.submit("value", newValueEntries);

    List<String> newMessages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(6, newMessages.size()); // Including the previous 8 messages + 3 new ones
    assertEquals("draw()", newMessages.get(3));
  }

  @Test
  public void testAddStockInvalidInput() {
    controller.enter("create");
    Map<String, String> createEntries = new HashMap<>();
    createEntries.put("name", "TestPortfolio");
    controller.submit("create", createEntries);

    controller.enter("add");
    Map<String, String> addEntries = new HashMap<>();
    addEntries.put("symbol", "INVALID");
    addEntries.put("shares", "-10");
    addEntries.put("day", "32");
    addEntries.put("month", "13");
    addEntries.put("year", "1999");
    controller.submit("add", addEntries);

    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(6, messages.size());
    assertEquals("setPanel(ActionSelection)", messages.get(0));
    assertEquals("draw()", messages.get(1));
    assertEquals("setPanel(create)", messages.get(2));
    assertEquals("setPanel(Display)", messages.get(3));
    assertEquals("setPanel(add)", messages.get(4));
    assertEquals("displayError(Invalid input provided.)", messages.get(5));
  }

  @Test
  public void testRemoveStockInvalidInput() {
    controller.enter("create");
    Map<String, String> createEntries = new HashMap<>();
    createEntries.put("name", "TestPortfolio");
    controller.submit("create", createEntries);

    controller.enter("add");
    Map<String, String> addEntries = new HashMap<>();
    addEntries.put("symbol", "AAPL");
    addEntries.put("shares", "10");
    addEntries.put("day", "1");
    addEntries.put("month", "1");
    addEntries.put("year", "2022");
    controller.submit("add", addEntries);

    controller.enter("remove");
    Map<String, String> removeEntries = new HashMap<>();
    removeEntries.put("symbol", "INVALID");
    removeEntries.put("shares", "-5");
    removeEntries.put("day", "32");
    removeEntries.put("month", "13");
    removeEntries.put("year", "1999");
    controller.submit("remove", removeEntries);

    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(8, messages.size());
    assertEquals("setPanel(ActionSelection)", messages.get(0));
    assertEquals("draw()", messages.get(1));
    assertEquals("setPanel(create)", messages.get(2));
    assertEquals("setPanel(Display)", messages.get(3));
    assertEquals("setPanel(add)", messages.get(4));
    assertEquals("setPanel(Display)", messages.get(5));
    assertEquals("setPanel(remove)", messages.get(6));
    assertEquals("displayError(Invalid input provided.)", messages.get(7));
  }

  @Test
  public void testPortfolioValueInvalidInput() {
    controller.enter("create");
    Map<String, String> createEntries = new HashMap<>();
    createEntries.put("name", "TestPortfolio");
    controller.submit("create", createEntries);

    controller.enter("add");
    Map<String, String> addEntries = new HashMap<>();
    addEntries.put("symbol", "AAPL");
    addEntries.put("shares", "10");
    addEntries.put("day", "1");
    addEntries.put("month", "1");
    addEntries.put("year", "2022");
    controller.submit("add", addEntries);

    controller.enter("value");
    Map<String, String> valueEntries = new HashMap<>();
    valueEntries.put("day", "32");
    valueEntries.put("month", "13");
    valueEntries.put("year", "1999");
    controller.submit("value", valueEntries);

    List<String> messages = ((MockGraphicalStockView) view).getMessages();
    assertEquals(8, messages.size());
    assertEquals("setPanel(ActionSelection)", messages.get(0));
    assertEquals("draw()", messages.get(1));
    assertEquals("setPanel(create)", messages.get(2));
    assertEquals("setPanel(Display)", messages.get(3));
    assertEquals("setPanel(add)", messages.get(4));
    assertEquals("setPanel(Display)", messages.get(5));
    assertEquals("setPanel(value)", messages.get(6));
    assertEquals("displayError(Invalid input provided.)", messages.get(7));
  }

}