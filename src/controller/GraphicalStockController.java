package controller;

import java.awt.Color;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;

import model.IStockBetterModel;
import view.ActionSelectionPanel;
import view.DisplayPanel;
import view.EntryPanel;
import view.IStockView;

/**
 * A GraphicalStockController is a GUI which receives and "parses" inputs by calling the
 * appropriate, corresponding method(s) in IStockBetterModel (via the 'control()' and 'enter()'
 * methods). In addition, the controller interacts with IStockView by displaying the output
 * graphically, and contains the method 'menu()' to display the available features of the program,
 * the method 'quit()' to terminate the program, and the methods 'enter()' and 'submit()' to process
 * the user input and return a result.
 */
public class GraphicalStockController implements IStockController, Features {
  private final IStockView view;
  private final IStockBetterModel model;
  private String selected; // The portfolio the user selected.

  /**
   * A GraphicalStockController is a GUI which receives and "parses" inputs by calling the
   * appropriate, corresponding method(s) in IStockBetterModel (via the 'control()' and 'enter()'
   * methods). In addition, the controller interacts with IStockView by displaying the output
   * graphically, and contains the method 'menu()' to display the available features of the program,
   * the method 'quit()' to terminate the program, and the methods 'enter()' and 'submit()' to
   * process the user input and return a result.
   *
   * @param model The model, which contains methods to compute the user input.
   * @param view  The view to display the output graphically to the user.
   */
  public GraphicalStockController(IStockBetterModel model, IStockView view) {
    this.model = model;
    this.view = view;
    selected = "";
  }

  /**
   * Display the menu screen containing controls to the user. Receive user input and call the
   * appropriate, corresponding method in IStockBetterModel. Display the GUI output to the user via
   * IStockView.
   */
  @Override
  public void control() {
    view.setFeatures(this); // Change the features that events are sent to.
    List<String> portfolios = model.getPortfolios(); // Retrieve the existing portfolios .
    // Automatically select the most recent portfolio.
    selected = portfolios.isEmpty() ? "" : portfolios.get(portfolios.size() - 1);
    view.setPanel(new ActionSelectionPanel(portfolios, selected));
    view.draw(); // Display the panel.
  }

  /**
   * Prompt user to enter inputs for given action.
   *
   * @param action The name of the intended action.
   */
  @Override
  public void enter(String action) {
    if (action.equals("create")) {
      // Display the entry panel for creating a portfolio.
      view.setPanel(new EntryPanel("create", new String[]{"name"}, false));
      return;
    }
    // If a portfolio is selected...
    if (!selected.isEmpty()) {
      switch (action) {
        case "add":
          // Display the entry panel for adding a quantity of a stock to a portfolio.
          view.setPanel(new EntryPanel("add", new String[]{"symbol", "shares"}, true));
          break;
        case "remove":
          // Display the entry panel for removing a quantity of a stock from a portfolio.
          view.setPanel(new EntryPanel("remove", new String[]{"symbol", "shares"}, true));
          break;
        case "value":
          // Display the entry panel for computing the value of a portfolio.
          view.setPanel(new EntryPanel("value", new String[]{}, true));
          break;
        case "composition":
          // Display the entry panel for displaying the quantity of each stock in a portfolio.
          view.setPanel(new EntryPanel("composition", new String[]{}, true));
          break;
        default:
          throw new IllegalStateException("Invalid action: " + action);
      }
    } else {
      view.displayError("Please create or select a profile");
    }
  }

  /**
   * Do action. {@code entries} stores inputs from view.
   *
   * @param actionName The name of the intended action.
   * @param entries    The map from field name to field value.
   */
  @Override
  public void submit(String actionName, Map<String, String> entries) {
    String name; // The name of the portfolio.
    String symbol; // The stock symbol.
    String shares; // The quantity of shares of the stock.
    int day; // The date component.
    int month; // The month component.
    int year; // The year component.
    boolean allValid; // Whether if every input is valid.
    Supplier<String> messageCreator; // Message to display to the GUI.

    switch (actionName) {
      case "create": // Create a portfolio.
        name = entries.get("name"); // Retrieve the name of the portfolio from the user entry box.
        allValid = !name.isEmpty(); // Valid if the input is not empty.
        // Display to the GUI that a portfolio has been created under the specified name.
        messageCreator = () -> {
          model.createPortfolio(name);
          setPortfolio(name);
          return "Portfolio '" + name + "' created.";
        };
        break;
      case "add": // Add a quantity of shares of a stock to a portfolio.
        symbol = entries.get("symbol"); // Retrieve the stock symbol from the user entry box.
        allValid = model.isValidSymbol(symbol); // Valid if the stock symbol exists in the API.
        shares = entries.get("shares"); // Retrieve the quantity of shares of the stock symbol.
        allValid &= isValidInteger(shares); // Valid if the quantity is an integer.
        day = Integer.parseInt(entries.get("day")); // Retrieve the date component.
        month = Integer.parseInt(entries.get("month")); // Retrieve the month component.
        year = Integer.parseInt(entries.get("year")); // Retrieve the year component.
        allValid &= isValidDate(day, month, year); // Valid if the date provided is a valid date.
        messageCreator =
            () -> {
              LocalDate date = LocalDate.of(year, month, day);
              model.addStockToPortfolioFile(selected, symbol, Integer.parseInt(shares), date);
              return "On " + date + ": Added '" + symbol + "' to Portfolio '" + selected + "'.";
            };
        break;
      case "remove": // Remove a quantity of shares of a stock from a portfolio.
        symbol = entries.get("symbol"); // Retrieve the stock symbol from the user entry box.
        allValid = model.isValidSymbol(symbol); // Valid if the stock symbol exists in the API.
        shares = entries.get("shares"); // Retrieve the quantity of shares of the stock symbol.
        allValid &= isValidInteger(shares); // Retrieve the quantity of shares of the stock symbol.
        day = Integer.parseInt(entries.get("day")); // Retrieve the date component.
        month = Integer.parseInt(entries.get("month")); // Retrieve the month component.
        year = Integer.parseInt(entries.get("year")); // Retrieve the year component.
        allValid &= isValidDate(day, month, year); // Valid if the date provided is a valid date.
        messageCreator =
            () -> {
              LocalDate date = LocalDate.of(year, month, day);
              model.removeStockFromPortfolioFile(selected, symbol, Integer.parseInt(shares), date);
              return "On " + date + ": Removed '" + symbol + "' from Portfolio '" + selected + "'.";
            };
        break;
      case "value": // Compute the total value of a portfolio.
        day = Integer.parseInt(entries.get("day")); // Retrieve the date component.
        month = Integer.parseInt(entries.get("month")); // Retrieve the month component.
        year = Integer.parseInt(entries.get("year")); // Retrieve the year component.
        allValid = isValidDate(day, month, year); // Valid if the date provided is a valid date.
        messageCreator = () -> "Value of '" + selected + "': $"
            + model.portfolioValue(selected, LocalDate.of(year, month, day));
        break;
      case "composition": // Display the quantity of each stock in the portfolio.
        day = Integer.parseInt(entries.get("day")); // Retrieve the date component.
        month = Integer.parseInt(entries.get("month")); // Retrieve the month component.
        year = Integer.parseInt(entries.get("year")); // Retrieve the year component.
        allValid = isValidDate(day, month, year); // Valid if the date provided is a valid date.
        messageCreator = () ->
            model.portfolioComposition(selected, LocalDate.of(year, month, day)).interpret();
        break;
      default:
        throw new IllegalStateException("Invalid action: " + actionName);
    }
    // If all inputs are valid, display the result to the GUI.
    if (allValid) {
      view.setPanel(new DisplayPanel(messageCreator.get()));
    } else {
      view.displayError("Invalid input provided.");
    }
  }

  /**
   * Notify user if {@code value} is invalid in relation to the given action.
   *
   * @param actionName The name of the intended action.
   * @param name       The name of the field.
   * @param value      The value of the field.
   * @param label      The label panel used for field entry.
   * @param entry      The input panel used for field entry.
   * @throws IllegalArgumentException If an unknown command is provided.
   */
  @Override
  public void validate(String actionName, String name, String value, JLabel label,
                       JComponent entry) throws IllegalArgumentException {
    switch (name) {
      case "name":
        if (actionName.equals("create")) {
          // Do not permit an empty name for a portfolio.
          changeValidation(label, entry, !value.isEmpty());
        } else {
          // Validate if the portfolio exists.
          changeValidation(label, entry, model.hasPortfolio(value));
        }
        break;
      case "symbol":
        // Validate if the stock symbol exists in the API.
        changeValidation(label, entry, model.isValidSymbol(value));
        break;
      case "shares":
        // Validate if a valid numerical value is provided.
        changeValidation(label, entry, isValidInteger(value));
        break;
      default:
        throw new IllegalStateException("Exception: Unknown command!");
    }
  }

  /**
   * Notify user if provided date is invalid.
   *
   * @param day     The given day.
   * @param month   The given month.
   * @param year    The given year.
   * @param dateBox The panel used for date entry.
   */
  @Override
  public void validateDate(int day, int month, int year, JPanel dateBox) {
    if (isValidDate(day, month, year)) {
      // If the date is valid, reflect by modifying the background to GREEN.
      dateBox.setBackground(Color.GREEN);
    } else {
      // If the date is invalid, reflect by modifying the background to GREEN.
      dateBox.setBackground(Color.RED);
    }
  }

  /**
   * Set the portfolio to perform operations on.
   *
   * @param portfolio The portfolio to select.
   */
  @Override
  public void setPortfolio(String portfolio) {
    selected = portfolio;
  }

  /**
   * Display the menu.
   */
  @Override
  public void menu() {
    view.setPanel(new ActionSelectionPanel(model.getPortfolios(), selected));
  }

  /**
   * Quit the stock simulation.
   */
  @Override
  public void quit() {
    System.exit(0);
  }

  private boolean isValidDate(int day, int month, int year) {
    try {
      LocalDate date = LocalDate.of(year, month, day);
      return date.isEqual(date);
    } catch (DateTimeException e) {
      return false;
    }
  }

  private boolean isValidInteger(String number) {
    try {
      return Integer.parseInt(String.valueOf(number)) >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private void changeValidation(JLabel label, JComponent entry, boolean valid) {
    if (valid) {
      label.setBackground(Color.GREEN);
      entry.setBackground(Color.GREEN);
    } else {
      label.setBackground(Color.RED);
      entry.setBackground(Color.RED);
    }
  }
}
