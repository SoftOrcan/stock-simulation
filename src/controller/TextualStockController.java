package controller;

import model.IStockModel;
import view.IStockView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A TextualStocksController receives and "parses" inputs by calling the appropriate, corresponding
 * method(s) in IStockModel (via the 'control()' and 'processCommand()' methods). In addition, the
 * controller interacts with IStockView by displaying the output textually, and contains the method
 * 'menu()' to display the available features of the program.
 */
public class TextualStockController implements IStockController {
  // The model, or "backend", which contains methods to "parse" the user input (i.e. compute the
  // result).
  private final IStockModel model;
  // The view, or "frontend", which displays the output textually to the user.
  private final IStockView view;

  /**
   * A TextualStocksController receives and "parses" inputs by calling the appropriate,
   * corresponding method(s) in IStockModel (via the 'control()' and 'processCommand()' methods). In
   * addition, the controller interacts with IStockView by displaying the output textually. This
   * constructor is public as it is called by tests beyond the package's scope.
   *
   * @param model The model, which contains methods to compute the user input.
   * @param view  The view to display the output textually to the user.
   */
  public TextualStockController(IStockModel model, IStockView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Display the menu to the user, and process user inputs by either calling methods in
   * IStockController or halt the program at the keystroke 'q'.
   */
  public void control() {
    Scanner sc = new Scanner(System.in);
    // Print the welcome message.
    view.displayMessage(menu());

    while (sc.hasNext()) { // Continue until the user quits.
      String userInstruction = sc.next(); // Take an instruction name.
      if (userInstruction.equals("q")) {
        break;
      } else {
        processCommand(userInstruction, sc, model);
      }
    }
  }

  /**
   * Process the user input by parsing and calling the corresponding method in IStockModel and
   * displaying the result via IStockView.
   *
   * @param userInstruction The user input to parse.
   * @param sc              The scanner to parse the user input.
   * @param model           The IStockModel to compute based on the command provided.
   */
  private void processCommand(String userInstruction, Scanner sc, IStockModel model) {
    String symbol; // The stock symbol to examine.
    LocalDate date; // The date to examine.
    LocalDate start; // The start date to examine.
    LocalDate end; // The end date to examine.
    String split;
    int numDays; // The range of days to de-advance.
    int amount; // The amount of stocks to purchase.

    switch (userInstruction) {
      case "stock-period": // Params: symbol, start, end
        view.displayMessage(promptSymbol());
        symbol = sc.next(); // Retrieve the stock name from the input.
        start = LocalDate.parse(sc.next()); // Retrieve the starting date from the input.
        end = LocalDate.parse(sc.next()); // Retrieve the ending date from the input.
        verifySymbol(symbol, () -> view.displayMessage("Gain/Loss of " + symbol + ": " +
            model.stockChange(symbol, start, end) + "."));
        break;
      case "stock-average": // Params: symbol, date, x
        symbol = sc.next(); // Retrieve the stock name from the input.
        date = LocalDate.parse(sc.next()); // Retrieve the date from the input.
        numDays = sc.nextInt(); // Retrieve the x-day quantity from the input.
        verifySymbol(symbol, () -> view.displayMessage("Moving average of " + symbol + ": " +
            model.stockMovingAverage(symbol, date, numDays) + "."));
        break;
      case "stock-crossovers": // Params: symbol, start, end, x
        symbol = sc.next(); // Retrieve the stock name from the input.
        start = LocalDate.parse(sc.next()); // Retrieve the starting date from the input.
        end = LocalDate.parse(sc.next()); // Retrieve the ending date from the input.
        numDays = sc.nextInt(); // Retrieve the x-day quantity from the input.
        verifySymbol(symbol, () -> view.displayMessage("Crossovers of " + symbol + ": " +
            model.stockCrossovers(symbol, start, end, numDays) + "."));
        break;
      case "stock-performance":
        symbol = sc.next(); // Retrieve the stock name from the input.
        start = LocalDate.parse(sc.next()); // Retrieve the starting date from the input.
        end = LocalDate.parse(sc.next()); // Retrieve the ending date from the input.
        amount = sc.nextInt();
        verifySymbol(symbol, () ->
            view.displayMessage(
                model.stockPerformance(symbol, start, end, amount).interpret()));
        break;
      case "portfolio-create": // Params: name
        final String portfolioNameCreate = sc.next(); // Retrieve the name of the portfolio from the input.
        model.createPortfolio(portfolioNameCreate);
        view.displayMessage("Created portfolio " + portfolioNameCreate + ".");
        break;
      case "portfolio-add": // Params: name, symbol, amount
        final String nameAdd = sc.next(); // Retrieve the name of the portfolio from the input.
        symbol = sc.next(); // Retrieve the stock name from the input.
        try {
          amount = sc.nextInt(); // Retrieve the quantity of stock purchased from the input.
          if (amount <= 0) {
            view.displayError("Must use positive amount.");
            view.displayMessage(menu());
          }
          date = LocalDate.parse(sc.next());
          verifyPortfolio(nameAdd, () -> verifySymbol(symbol, () -> {
            model.addStockToPortfolio(nameAdd, symbol, amount, date);
            view.displayMessage("On " + date + ": Added " + symbol + " (amt: " + amount + ") to "
                + "portfolio '" + nameAdd + "'.");
          }));
        } catch (InputMismatchException e) {
          view.displayError("Must use integer for amount.");
          view.displayMessage(menu());
        }
        break;
      case "portfolio-remove": // Params: name, symbol
        // Retrieve the name of the portfolio from the input.
        final String nameRemove = sc.next();
        symbol = sc.next(); // Retrieve the stock name from the input.
        try {
          amount = sc.nextInt();
          date = LocalDate.parse(sc.next());
          verifyPortfolio(nameRemove, () -> {
            model.removeStockFromPortfolio(nameRemove, symbol, amount, date);
            view.displayMessage("On " + date + ": Removed " + symbol + " (amt: " + amount + ") "
                + "from " + "portfolio '" + nameRemove + "'.");
          });
        } catch (InputMismatchException e) {
          view.displayError("Must use integer for amount.");
          view.displayMessage(menu());
        }
        break;
      case "portfolio-composition":
        // Retrieve the name of the portfolio from the input.
        final String nameComposition = sc.next();
        date = LocalDate.parse(sc.next()); // Retrieve the date from the input.
        verifyPortfolio(nameComposition, () ->
            view.displayMessage(model.portfolioComposition(nameComposition, date).interpret()));
        break;
      case "portfolio-distribution":
        // Retrieve the name of the portfolio from the input.
        final String nameDistribution = sc.next();
        date = LocalDate.parse(sc.next()); // Retrieve the date from the input.
        verifyPortfolio(nameDistribution,
            () -> view.displayMessage(
                model.portfolioDistribution(nameDistribution, date).interpret()));
        break;
      case "portfolio-value": // Params: name, date
        // Retrieve the name of the portfolio from the input.
        final String nameValue = sc.next();
        date = LocalDate.parse(sc.next()); // Retrieve the date from the input.
        verifyPortfolio(nameValue, () ->
            view.displayMessage("Value of " + nameValue + ": " +
                model.portfolioValue(nameValue, date) + "."));
        break;
      case "portfolio-rebalance":
        final String nameRebalance = sc.next();
        split = sc.next();
        // TODO - verify split
        List<Integer> numericalSplit =
            Arrays.stream(split.split("-"))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        date = LocalDate.parse(sc.next());
        verifyPortfolio(nameRebalance,
            () -> view.displayMessage(
                model.portfolioRebalance(nameRebalance, numericalSplit, date).interpret()));
      case "portfolio-performance": // Params: name, date
        // Retrieve the stock name from the input.
        final String namePerformance = sc.next();
        start = LocalDate.parse(sc.next()); // Retrieve the starting date from the input.
        end = LocalDate.parse(sc.next()); // Retrieve the ending date from the input.
        amount = sc.nextInt();
        verifyPortfolio(namePerformance, () -> view.displayMessage(
            model.portfolioPerformance(namePerformance, start, end, amount).interpret()));
        break;
      default:
        view.displayMessage("Undefined instruction: " + userInstruction + ".");
        view.displayMessage(menu());
    }
  }

  /**
   * Validate a stock is an available symbol through the AlphaVantage API. If the symbol exists,
   * call a command, specified by the user instruction.
   *
   * @param symbol The stock symbol to validate.
   * @param macro  The command to execute.
   */
  private void verifySymbol(String symbol, Runnable macro) {
    if (model.isValidSymbol(symbol)) {
      macro.run();
    } else {
      view.displayError("Stock symbol " + symbol + " does not exist.");
      view.displayMessage(menu());
    }
  }

  /**
   * Validate a portfolio exists based on its name. If the portfolio exists, call a command,
   * specified by the user instruction.
   *
   * @param name  The portfolio name to validate.
   * @param macro The command to execute.
   */
  private void verifyPortfolio(String name, Runnable macro) {
    if (model.hasPortfolio(name)) {
      macro.run();
    } else {
      view.displayError("Portfolio " + name + " does not exist.");
      view.displayMessage(menu());
    }
  }

  private String promptSymbol() {
    return "Enter a VALID stock symbol (e.g. 'AAPL' for Apple or 'KO' for Cola-Cola Company): ";
  }

  private String promptYear() {
    return "Enter a VALID year (e.g. '2024' or '2017'): ";
  }

  /**
   * Provide the available features of the program and the parameters which the methods require to
   * compute the result.
   *
   * @return A String displaying the available features of the program.
   */
  private String menu() {
    String ls = System.lineSeparator();
    return "Dates must be parsable by LocalDate::parse (e.g. 2024-06-06)." + ls + ls +
        "1) stock-period [symbol] [start-date] [end-date]" + ls +
        "2) stock-average [symbol] [date] [number of days]" + ls +
        "3) stock-crossovers [symbol] [start-date] [end-date] [number of days]" + ls +
        "4) stock-performance [symbol] [start-date] [end-date] [x-label count]" + ls +
        "5) portfolio-create [name]" + ls +
        "6) portfolio-add [name] [symbol] [amount] [date]" + ls +
        "7) portfolio-remove [name] [symbol] [amount] [date]" + ls +
        "8) portfolio-composition [name] [date]" + ls +
        "9) portfolio-distribution [name] [date]" + ls +
        "10) portfolio-value [name] [date]" + ls +
        "11) portfolio-rebalance [name] [split] [date]" + ls +
        "12) portfolio-performance [name] [start-date] [end-date] [x-label count]" + ls +
        "13) b: return to previous page" + ls +
        "14) q: quit the program" + ls + ls +
        "Enter numerical option (e.g. Enter '1' for stock-period): ";
  }
}
