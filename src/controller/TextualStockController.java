package controller;

import model.IStockModel;
import view.IStockView;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A TextualStocksController receives and "parses" inputs by calling the appropriate, corresponding
 * method(s) in IStockModel (via the 'control()' and 'processCommand()' methods). In addition, the
 * controller interacts with IStockView by displaying the output textually, and contains the
 * method 'menu()' to display the available features of the program.
 */
public class TextualStockController implements IStockController {
  // The model, or "backend", which contains methods to "parse" the user input (i.e. compute
  // the result).
  private final IStockModel model;
  // The view, or "frontend", which displays the output textually to the user.
  private final IStockView view;

  /**
   * A TextualStocksController receives and "parses" inputs by calling the appropriate,
   * corresponding method(s) in IStockModel (via the 'control()' and 'processCommand()' methods).
   * In addition, the controller interacts with IStockView by displaying the output textually.
   * This constructor is public as it is called by tests beyond the package's scope.
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
      if (userInstruction.equals("q") || userInstruction.equals("quit")) {
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
    String name; // The name of a portfolio.
    int numDays; // The range of days to de-advance.
    int amount; // The amount of stocks to purchase.

    switch (userInstruction) {
      case "stock-period": // Params: symbol, start, end
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
        name = sc.next(); // Retrieve the name of the portfolio from the input.
        model.createPortfolio(name);
        view.displayMessage("Created portfolio " + name + ".");
        break;
      case "portfolio-add": // Params: name, symbol, amount
        name = sc.next(); // Retrieve the name of the portfolio from the input.
        symbol = sc.next(); // Retrieve the stock name from the input.
        try {
          amount = sc.nextInt(); // Retrieve the quantity of stock purchased from the input.
          if (amount <= 0) {
            view.displayError("Must use positive amount.");
            view.displayMessage(menu());
          }
          date = LocalDate.parse(sc.next());
          verifyPortfolio(name, () -> verifySymbol(symbol, () -> {
            model.addStockToPortfolio(name, symbol, amount, date);
            view.displayMessage("On " + date + ": Added " + symbol + " (amt: " + amount + ") to "
                    + "portfolio '" + name + "'.");
          }));
        } catch (InputMismatchException e) {
          view.displayError("Must use integer for amount.");
          view.displayMessage(menu());
        }
        break;
      case "portfolio-remove": // Params: name, symbol
        name = sc.next(); // Retrieve the name of the portfolio from the input.
        symbol = sc.next(); // Retrieve the stock name from the input.
        try {
          amount = sc.nextInt();
          date = LocalDate.parse(sc.next());
          verifyPortfolio(name, () -> {
            model.removeStockFromPortfolio(name, symbol, amount, date);
            view.displayMessage("On " + date + ": Removed " + symbol + " (amt: " + amount + ") "
                    + "from " + "portfolio '" + name + "'.");
          });
        } catch (InputMismatchException e) {
          view.displayError("Must use integer for amount.");
          view.displayMessage(menu());
        }
        break;
      case "portfolio-composition":
        name = sc.next(); // Retrieve the name of the portfolio from the input.
        date = LocalDate.parse(sc.next()); // Retrieve the date from the input.
        verifyPortfolio(name,
                () -> view.displayMessage(model.portfolioComposition(name, date).interpret()));
        break;
      case "portfolio-distribution":
        name = sc.next(); // Retrieve the name of the portfolio from the input.
        date = LocalDate.parse(sc.next()); // Retrieve the date from the input.
        verifyPortfolio(name,
                () -> view.displayMessage(model.portfolioDistribution(name, date).interpret()));
        break;
      case "portfolio-value": // Params: name, date
        name = sc.next(); // Retrieve the name of the portfolio from the input.
        date = LocalDate.parse(sc.next()); // Retrieve the date from the input.
        verifyPortfolio(name, () -> view.displayMessage("Value of " + name + ": "
                + model.portfolioValue(name, date) + "."));
        break;
      case "portfolio-performance": // Params: name, date
        name = sc.next(); // Retrieve the stock name from the input.
        start = LocalDate.parse(sc.next()); // Retrieve the starting date from the input.
        end = LocalDate.parse(sc.next()); // Retrieve the ending date from the input.
        amount = sc.nextInt();
        verifyPortfolio(name, () -> view.displayMessage(
                model.portfolioPerformance(name, start, end, amount).interpret()));
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

  /**
   * Provide the available features of the program and the parameters which the methods require
   * to compute the result.
   *
   * @return A String displaying the available features of the program.
   */
  private String menu() {
    String ls = System.lineSeparator();
    return "Dates must be parsable by LocalDate::parse (e.g. 2024-06-06)." + ls + ls +
            "stock-period [symbol] [start-date] [end-date]" + ls +
            "stock-average [symbol] [date] [number of days]" + ls +
            "stock-crossovers [symbol] [start-date] [end-date] [number of days]" + ls +
            "stock-performance [symbol] [start-date] [end-date] [x-label count]" + ls +
            "portfolio-create [name]" + ls +
            "portfolio-add [name] [symbol] [amount] [date]" + ls +
            "portfolio-remove [name] [symbol] [amount] [date]" + ls +
            "portfolio-composition [name] [date]" + ls +
            "portfolio-distribution [name] [date]" + ls +
            "portfolio-value [name] [date]" + ls +
            "portfolio-performance [name] [start-date] [end-date] [x-label count]" + ls +
            "q: quit the program" + ls + ls +
            "Enter instruction: ";
  }
}
