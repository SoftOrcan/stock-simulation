package controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Scanner;

import model.IStockModel;
import view.IStockView;

public class TextualStockController implements IStockController {
  private final String ls = System.lineSeparator();
  private final IStockView view;
  private final IStockModel model;

  public TextualStockController(IStockModel model, IStockView view) {
    this.view = view;
    this.model = model;
  }

  public void control() {
    Scanner sc = new Scanner(System.in);
    view.displayMessage(menu());

    while (sc.hasNext()) {
      String userInstruction = sc.next();
      if (userInstruction.equals("q")) {
        break;
      } else if (userInstruction.equals("m")) {
        view.displayMessage(menu());
      }
      else {
        processCommand(userInstruction, sc, model);
      }
    }
  }

  private void processCommand(String userInstruction, Scanner sc, IStockModel model) {
    String name;
    String symbol;
    LocalDate date;
    LocalDate start;
    LocalDate end;
    List<Integer> split;
    int numDays;
    int amount;

    switch (userInstruction) {
      case "1":
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
      case "2":
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
      case "3":
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
        view.displayMessage("Value of " + name + ": " + model.portfolioValue(name, date) + ".");
        view.displayMessage(returnToMenu());
        break;
      default:
        view.displayMessage("Undefined instruction: " + userInstruction + ".");
        view.displayMessage(menu());
    }
  }

  String validateName(Scanner sc) {
    String prompt = "Enter portfolio name:";
    view.displayMessage(prompt);

    return sc.next();
  }

  String validatePortfolio(Scanner sc) {
    String prompt = "Enter portfolio name to modify/view:";
    view.displayMessage(prompt);

    String name = sc.next();

    while (!model.hasPortfolio(name)) {
      view.displayError("Portfolio " + name + " does not exist.");
      view.displayMessage(prompt);
      name = sc.next();
    }

    return name;
  }

  String validateSymbol(Scanner sc) {
    String prompt = "Enter stock symbol (e.g. 'AAPL' for Apple or 'KO' for Cola-Cola Company):";
    view.displayMessage(prompt);

    String symbol = sc.next();

    while (!model.isValidSymbol(symbol)) {
      view.displayError("Stock symbol '" + symbol + "' does not exist.");
      view.displayMessage(prompt);
      symbol = sc.next();
    }

    return symbol;
  }

  int validateNatural(Scanner sc) {
    String prompt = "Enter a POSITIVE numerical value:";
    view.displayMessage(prompt);

    String value = sc.next();

    while (!isValidInteger(value)) {
      view.displayError("The numerical value " + value + " is invalid.");
      view.displayMessage(prompt);
      value = sc.next();
    }

    return Integer.parseInt(value);
  }

  boolean isValidInteger(String number) {
    try {
      int value = Integer.parseInt(String.valueOf(number));
      return value >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  String validateDate(Scanner sc, String dateComponent, int dateType) {
    String type = (dateType == 1) ? "START " : (dateType == 2) ? "END " : "";
    String component =
        dateComponent.equals("year") ? "year (e.g. '2024' or '2000'):" :
            (dateComponent.equals("month") ?
                "month (i.e. from '01' to '12'):" :
                "day (e.g. '04', '01', or '19'):");
    String prompt = "Enter " + type + component;

    view.displayMessage(prompt);

    String value = sc.next();

    while (!isValidDate(value, dateComponent)) {
      view.displayError("The " + dateComponent + " is invalid.");
      view.displayMessage(prompt);
      value = sc.next();
    }

    return value;
  }

  boolean isValidDate(String value, String dateComponent) {
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

  String returnToMenu() {
    return "m: return to menu";
  }

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