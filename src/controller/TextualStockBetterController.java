package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.IStockBetterModel;
import view.IStockView;

/**
 * A TextualStocksBetterController receives and "parses" inputs by calling the appropriate,
 * corresponding method(s) in IStockBetterModel (via the 'control()' and 'processCommand()' methods)
 * . In addition, the controller interacts with IStockView by displaying the output textually, and
 * contains the method 'menu()' to display the available features of the program.
 */
public class TextualStockBetterController extends TextualStockController implements
    IStockBetterController {
  private final String ls = System.lineSeparator();
  // The view, or "frontend", which displays the output textually to the user.
  private final IStockView view;
  // The model, or "backend", which contains methods to "parse" the user input (i.e. compute
  // the result).
  private final IStockBetterModel model;
  private final Scanner sc;

  /**
   * A TextualStocksBetterController receives and "parses" inputs by calling the appropriate,
   * corresponding method(s) in IStockBetterModel (via the 'control()' and 'processCommand()'
   * methods). In addition, the controller interacts with IStockView by displaying the output
   * textually. This constructor is public as it is called by tests beyond the package's scope.
   *
   * @param model The model, which contains methods to compute the user input.
   * @param view  The view to display the output textually to the user.
   * @param in    Readable to read the user input.
   */
  public TextualStockBetterController(IStockBetterModel model, IStockView view, Readable in) {
    super(model, view, in);
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
  private void processCommand(String userInstruction, IStockBetterModel model) {
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
        view.displayMessage(returnToMenu());
        break;
      case "5":
        name = validateName(sc);
        model.createPortfolio(name);
        view.displayMessage("Created portfolio '" + name + "'.");
        view.displayMessage(returnToMenu());
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
        view.displayMessage(returnToMenu());
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
        view.displayMessage(returnToMenu());
        break;
      case "8":
        name = validatePortfolio(sc);
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        view.displayMessage(model.portfolioComposition(name, date).interpret());
        view.displayMessage(returnToMenu());
        break;
      case "9":
        name = validatePortfolio(sc);
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        view.displayMessage(model.portfolioDistribution(name, date).interpret());
        view.displayMessage(returnToMenu());
        break;
      case "10":
        name = validatePortfolio(sc);
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        view.displayMessage("Value of " + name + ": $" + model.portfolioValue(name, date) + ".");
        view.displayMessage(returnToMenu());
        break;
      case "11":
        name = validatePortfolio(sc);
        split = validateSplit(name, sc).stream().map(Integer::valueOf).collect(Collectors.toList());
        date = LocalDate.parse(
            validateDate(sc, "year", 3) + "-"
                + validateDate(sc, "month", 3) + "-"
                + validateDate(sc, "day", 3));
        model.portfolioRebalance(name, split, date);
        view.displayMessage("Rebalanced portfolio '" + name + "'.");
        view.displayMessage(returnToMenu());
        break;
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
        view.displayMessage(returnToMenu());
        break;
      default:
        view.displayMessage("Undefined instruction: " + userInstruction + ".");
        view.displayMessage(menu());
    }
  }

  private List<String> validateSplit(String name, Scanner sc) {
    String prompt = "Enter split for each of the stock (e.g. '50-50' or '30-40-30'):";
    view.displayMessage(prompt);

    List<String> split = List.of(sc.next().split("-"));

    while (!model.isValidSplit(name, split)) {
      view.displayError("The split " + split + " contains non-numeric values or do not contain "
          + "the split for every stock in portfolio '" + name + "'.");
      view.displayMessage(prompt);
      split = List.of(sc.next().split("-"));
    }

    return split;
  }

  /**
   * Provide the available features of the program and the parameters which the methods require
   * to compute the result.
   *
   * @return A String displaying the available features of the program.
   */
  @Override
  protected String menu() {
    return "Dates must be parsable by LocalDate::parse (e.g. 2024-06-06)." + ls +
        "1) stock-change" + ls +
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
        "q: quit the program" + ls + ls +
        "Enter numerical option (e.g. Enter '1' for stock-change):";
  }
}
