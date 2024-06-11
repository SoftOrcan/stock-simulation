package controller;

import model.IStockModel;
import view.IStockView;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TextualStockController implements IStockController {
  private final String ls = System.lineSeparator();
  private final IStockModel model;
  private final IStockView view;

  public TextualStockController(IStockModel model, IStockView view) {
    this.model = model;
    this.view = view;
  }

  public void control() {
    Scanner sc = new Scanner(System.in);
    view.displayMessage(menu());

    while (sc.hasNext()) {
      String userInstruction = sc.next();
      if (userInstruction.equals("q")) {
        break;
      } else {
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
        break;
      case "4":
        symbol = validateSymbol(sc);
        start = LocalDate.parse(
            validateDate(sc, "year", 1) + "-"
                + validateDate(sc, "month", 1) + "-"
                + validateDate(sc, "day", 1));
        end = LocalDate.parse(
            validateDate(sc, "year", 2) + "-"
                + validateDate(sc, "month", 2) + "-"
                + validateDate(sc, "day", 2));
        amount = validateNatural(sc);
        view.displayMessage(model.stockPerformance(symbol, start, end, amount).interpret());
        break;
      case "5":
        name = validateName(sc);
        model.createPortfolio(name);
        view.displayMessage("Created portfolio '" + name + "'.");
        break;
      case "6":
        name = validatePortfolio(sc);
        symbol = validateSymbol(sc);
        amount = validateNatural(sc);
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        model.addStockToPortfolio(name, symbol, amount, date);
        view.displayMessage("On " + date + ": Added '" + symbol + "' (quantity: " + amount + ") to "
            + "portfolio '" + name + "'.");
        break;
      case "7":
        name = validatePortfolio(sc);
        symbol = validateSymbol(sc);
        amount = validateNatural(sc);
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        model.removeStockFromPortfolio(name, symbol, amount, date);
        view.displayMessage("On " + date + ": Removed '" + symbol + "' (quantity: " + amount + ") "
            + "from portfolio '" + name + "'.");
        break;
      case "8":
        name = validatePortfolio(sc);
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        view.displayMessage(model.portfolioComposition(name, date).interpret());
        break;
      case "9":
        name = validatePortfolio(sc);
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        view.displayMessage(model.portfolioDistribution(name, date).interpret());
        break;
      case "10":
        name = validatePortfolio(sc);
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        view.displayMessage("Value of " + name + ": " + model.portfolioValue(name, date) + ".");
        break;
      case "11":
        name = validatePortfolio(sc);
        split = validateSplit(name, sc).stream().map(Integer::valueOf).collect(Collectors.toList());
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        view.displayMessage(model.portfolioRebalance(name, split, date).interpret());
      case "12":
        name = validatePortfolio(sc);
        start = LocalDate.parse(
            validateDate(sc, "year", 1) + "-"
                + validateDate(sc, "month", 1) + "-"
                + validateDate(sc, "day", 1));
        end = LocalDate.parse(
            validateDate(sc, "year", 2) + "-"
                + validateDate(sc, "month", 2) + "-"
                + validateDate(sc, "day", 2));
        amount = validateNatural(sc);
        view.displayMessage(model.portfolioPerformance(name, start, end, amount).interpret());
        break;
      default:
        view.displayMessage("Undefined instruction: " + userInstruction + ".");
        view.displayMessage(menu());
    }
  }

  private String validateName(Scanner sc) {
    String prompt = "Enter portfolio name:";
    view.displayMessage(prompt);

    return sc.next();
  }

  private String validatePortfolio(Scanner sc) {
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

  private String validateSymbol(Scanner sc) {
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

  private int validateNatural(Scanner sc) {
    String prompt = "Enter a numerical value:";
    view.displayMessage(prompt);

    String value = sc.next();

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

  private String validateDate(Scanner sc, String dateComponent, int dateType) {
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

  private List<String> validateSplit(String name, Scanner sc) {
    String prompt = "Enter split for each of the stock (e.g. '50-50' or '30-40-30'):";
    List<String> split = List.of(sc.next().split("-"));

    while (!model.isValidSplit(name, split)) {
      view.displayError("The split " + split + " contains non-numeric values or do not contain "
          + "the split for every stock in portfolio '" + name + "'.");
      view.displayMessage(prompt);
      split = List.of(sc.next().split("-"));
    }

    return split;
  }

  private String menu() {
    return "Dates must be parsable by LocalDate::parse (e.g. 2024-06-06)." + ls +
        "1) stock-period" + ls +
        "2) stock-average" + ls +
        "3) stock-crossovers" + ls +
        "4) stock-performance" + ls +
        "5) portfolio-create" + ls +
        "6) portfolio-add" + ls +
        "7) portfolio-remove" + ls +
        "8) portfolio-composition" + ls +
        "9) portfolio-distribution" + ls +
        "10) portfolio-value" + ls +
        "11) portfolio-rebalance" + ls +
        "12) portfolio-performance" + ls +
        "13) q: quit the program" + ls + ls +
        "Enter numerical option (e.g. Enter '1' for stock-period): ";
  }
}