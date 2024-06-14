package controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Scanner;

import model.IStockModel;
import view.IStockView;

/**
 * A TextualStocksController receives and "parses" inputs by calling the appropriate, corresponding
 * method(s) in IStockModel (via the 'control()' and 'processCommand()' methods). In addition, the
 * controller interacts with IStockView by displaying the output textually, and contains the method
 * 'menu()' to display the available features of the program.
 */
public class TextualStockController implements IStockController {
  private final String ls = System.lineSeparator();
  // The view, or "frontend", which displays the output textually to the user.
  private final IStockView view;
  // The model, or "backend", which contains methods to "parse" the user input (i.e. compute
  // the result).
  private final IStockModel model;
  private final Scanner sc;

  /**
   * A TextualStocksController receives and "parses" inputs by calling the appropriate,
   * corresponding method(s) in IStockModel (via the 'control()' and 'processCommand()' methods). In
   * addition, the controller interacts with IStockView by displaying the output textually. This
   * constructor is public as it is called by tests beyond the package's scope.
   *
   * @param model The model, which contains methods to compute the user input.
   * @param view  The view to display the output textually to the user.
   * @param in    Readable to read the user input.
   */
  public TextualStockController(IStockModel model, IStockView view, Readable in) {
    this.view = view;
    this.model = model;
    this.sc = new Scanner(in);
  }

  /**
   * Display the menu to the user, and process user inputs by either calling methods in
   * IStockController or halt the program at the keystroke 'q'.
   */
  public void control() {
    view.displayMessage(menu());

    while (sc.hasNext()) {
      String userInstruction = sc.next();
      if (userInstruction.equals("q")) {
        break;
      } else if (userInstruction.equals("m")) {
        view.displayMessage(menu());
      } else {
        processCommand(userInstruction, model);
      }
    }
  }

  /**
   * Process the user input by parsing and calling the corresponding method in IStockModel and
   * displaying the result via IStockView.
   *
   * @param userInstruction The user input to parse.
   * @param model           The IStockModel to compute based on the command provided.
   */
  private void processCommand(String userInstruction, IStockModel model) {
    String name; // The name of a portfolio.
    String symbol; // The stock symbol to examine.
    LocalDate date; // The date to examine.
    LocalDate start; // The start date to examine.
    LocalDate end; // The end date to examine.
    int numDays; // The range of days to de-advance.
    int amount; // The amount of stocks to purchase.

    switch (userInstruction) {
      case "1": // stock-change
        symbol = validateSymbol(sc);
        start = LocalDate.parse(
            validateDate(sc, "year", 1) + "-"
                + validateDate(sc, "month", 1) + "-"
                + validateDate(sc, "day", 1));
        end = LocalDate.parse(
            validateDate(sc, "year", 2) + "-"
                + validateDate(sc, "month", 2) + "-"
                + validateDate(sc, "day", 2));
        view.displayMessage("Gain/Loss of '" + symbol + "': "
            + model.stockChange(symbol, start, end) + ".");
        view.displayMessage(returnToMenu());
        break;
      case "2": // stock-moving-average
        symbol = validateSymbol(sc);
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        numDays = validateNatural(sc);
        view.displayMessage("Moving average of '" + symbol + "': "
            + model.stockMovingAverage(symbol, date, numDays) + ".");
        view.displayMessage(returnToMenu());
        break;
      case "3": // stock-crossovers
        symbol = validateSymbol(sc);
        start = LocalDate.parse(
            validateDate(sc, "year", 1) + "-"
                + validateDate(sc, "month", 1) + "-"
                + validateDate(sc, "day", 1));
        end = LocalDate.parse(
            validateDate(sc, "year", 2) + "-"
                + validateDate(sc, "month", 2) + "-"
                + validateDate(sc, "day", 2));
        numDays = validateNatural(sc);
        view.displayMessage("Crossovers of '" + symbol + "': "
            + model.stockCrossovers(symbol, start, end, numDays) + ".");
        view.displayMessage(returnToMenu());
        break;
      case "4":
        name = validateName(sc);
        model.createPortfolio(name);
        view.displayMessage("Created portfolio '" + name + "'.");
        view.displayMessage(returnToMenu());
        break;
      case "5":
        name = validatePortfolio(sc);
        symbol = validateSymbol(sc);
        amount = validateNatural(sc);
        model.addStockToPortfolio(name, symbol, amount);
        view.displayMessage("Added '" + symbol + "' (quantity: " + amount + ") to portfolio '"
            + name + "'.");
        view.displayMessage(returnToMenu());
        break;
      case "6":
        name = validatePortfolio(sc);
        symbol = validateSymbol(sc);
        amount = validateNatural(sc);
        model.removeStockFromPortfolio(name, symbol, amount);
        view.displayMessage("Removed '" + symbol + "' (quantity: " + amount + ") from portfolio '"
            + name + "'.");
        view.displayMessage(returnToMenu());
        break;
      case "7":
        name = validatePortfolio(sc);
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        view.displayMessage("Value of " + name + ": $" + model.portfolioValue(name, date) + ".");
        view.displayMessage(returnToMenu());
        break;
      default:
        view.displayMessage("Undefined instruction: " + userInstruction + ".");
        view.displayMessage(menu());
    }
  }

  /**
   * Validate the name of a portfolio.
   *
   * @param sc Scanner to read the user input.
   * @return The following command to the user input.
   */
  String validateName(Scanner sc) {
    String prompt = "Enter portfolio name:";
    view.displayMessage(prompt);

    return sc.next();
  }

  /**
   * Validate a portfolio, ensuring it exists at the call of the command.
   *
   * @param sc Scanner to read the user input.
   * @return The name of the portfolio, if it exists. Otherwise, request the user for another name.
   */
  String validatePortfolio(Scanner sc) {
    String prompt = "Enter portfolio name to modify/view:";
    view.displayMessage(prompt);

    String name = sc.next();

    // If the portfolio does not exist, request the user for another name
    while (!model.hasPortfolio(name)) {
      view.displayError("Portfolio '" + name + "' does not exist.");
      view.displayMessage(prompt);
      name = sc.next();
    }

    return name;
  }

  /**
   * Validate the stock symbol, ensuring it exists in the API.
   *
   * @param sc Scanner to read the user input.
   * @return The name of the stock symbol, if it exists.
   */
  String validateSymbol(Scanner sc) {
    String prompt = "Enter stock symbol (e.g. 'AAPL' for Apple or 'KO' for Cola-Cola Company):";
    view.displayMessage(prompt);

    String symbol = sc.next();

    // If the symbol does not exist, prompt the user for another symbol name.
    while (!model.isValidSymbol(symbol)) {
      view.displayError("Stock symbol '" + symbol + "' does not exist.");
      view.displayMessage(prompt);
      symbol = sc.next();
    }

    return symbol;
  }

  /**
   * Validate a number, ensuring it is a natural.
   *
   * @param sc Scanner to read the user input.
   * @return The number, if it is a valid natural.
   */
  int validateNatural(Scanner sc) {
    String prompt = "Enter a POSITIVE numerical value:";
    view.displayMessage(prompt);

    String value = sc.next();

    // If the input is not a natural number, prompt the user for another input.
    while (!isValidInteger(value)) {
      view.displayError("The numerical value " + value + " is invalid.");
      view.displayMessage(prompt);
      value = sc.next();
    }

    return Integer.parseInt(value);
  }

  private boolean isValidInteger(String number) {
    try {
      int value = Integer.parseInt(String.valueOf(number));
      return value >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Validate the date of the user input, ensuring a valid date is entered.
   *
   * @param sc            Scanner to read the user input.
   * @param dateComponent The date component to examine.
   * @param dateType      The date type (i.e. "START" or "END").
   * @return The date of the user input, if it is a valid date.
   */
  String validateDate(Scanner sc, String dateComponent, int dateType) {
    // Determine the type (i.e. "START or "END).
    String type = (dateType == 1) ? "START " : (dateType == 2) ? "END " : "";
    // Determine the component of the date (i.e. between a day, month or year).
    String component =
        dateComponent.equals("year") ? "year (e.g. '2024' or '2000'):" :
            (dateComponent.equals("month") ?
                "month (i.e. from '01' to '12'):" :
                "day (e.g. '04', '01', or '19'):");
    String prompt = "Enter " + type + component;

    view.displayMessage(prompt);

    String value = sc.next();

    // If the date is an invalid input, prompt the user for another date.
    while (!isValidDate(value, dateComponent)) {
      view.displayError("The " + dateComponent + " is invalid.");
      view.displayMessage(prompt);
      value = sc.next();
    }

    return value;
  }

  private boolean isValidDate(String value, String dateComponent) {
    try {
      switch (dateComponent) {
        case "year":
          if (value.length() != 4) {
            return false;
          }
          Year.of(Integer.parseInt(value));
          break;
        case "month":
          if (value.length() != 2) {
            return false;
          }
          Month.of(Integer.parseInt(value));
          break;
        case "day":
          if (value.length() != 2) {
            return false;
          }
          int day = Integer.parseInt(value);
          if (day < 1 || day > 31) {
            return false;
          }
          break;
        default:
          return false; // Invalid date component
      }
      return true;
    } catch (DateTimeException | NumberFormatException e) {
      return false;
    }
  }

  /**
   * Ask the user to return to the menu screen, or exit.
   *
   * @return Ask the user to return to the menu screen, or exit.
   */
  String returnToMenu() {
    return "m: return to menu" + ls + "q: quit the program";
  }

  /**
   * Provide the available features of the program and the parameters which the methods require
   * to compute the result.
   *
   * @return A String displaying the available features of the program.
   */
  protected String menu() {
    return "Dates must be parsable by LocalDate::parse (e.g. 2024-06-06)." + ls +
        "1) stock-change" + ls +
        "2) stock-average" + ls +
        "3) stock-crossovers" + ls +
        "4) portfolio-create" + ls +
        "5) portfolio-add" + ls +
        "6) portfolio-remove" + ls +
        "7) portfolio-value" + ls +
        "q: quit the program" + ls + ls +
        "Enter numerical option (e.g. Enter '1' for stock-change):";
  }
}