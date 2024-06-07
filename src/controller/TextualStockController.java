package controller;

import model.IStockModel;
import view.IStockView;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * A TextualStocksController 
 */
public class TextualStockController implements IStockController {
  private final IStockModel model;
  private final IStockView view;

  public TextualStockController(IStockModel model, IStockView view) {
    this.model = model;
    this.view = view;
  }

  public void control() {
    Scanner sc = new Scanner(System.in);
    // Print the welcome message.
    view.displayMessage(menu());

    while (sc.hasNext()) { // Continue until the user quits.
      String userInstruction = sc.next(); // Take an instruction name.
      if (userInstruction.equals("quit") || userInstruction.equals("q")) {
        break;
      } else {
        processCommand(userInstruction, sc, model);
      }
    }
  }

  private void processCommand(String userInstruction, Scanner sc, IStockModel model) {
    String symbol;
    LocalDate date;
    LocalDate start;
    LocalDate end;
    String name;
    int x;
    int amount;

    switch (userInstruction) {
      case "stock-examine-period": // Params: symbol, start, end
        symbol = sc.next();
        start = LocalDate.parse(sc.next());
        end = LocalDate.parse(sc.next());
        view.displayMessage("Gain/Loss of " + symbol + ": " +
                model.calculateChange(symbol, start, end) + ".");
        break;
      case "stock-examine-average": // Params: symbol, date, x
        symbol = sc.next();
        date = LocalDate.parse(sc.next());
        x = sc.nextInt();
        view.displayMessage("Moving average of " + symbol + ": " +
                model.movingAverage(symbol, date, x) + ".");
        break;
      case "stock-examine-crossovers": // Params: symbol, start, end, x
        symbol = sc.next();
        start = LocalDate.parse(sc.next());
        end = LocalDate.parse(sc.next());
        x = sc.nextInt();
        view.displayMessage("Crossovers of " + symbol + ": " +
                model.findCrossovers(symbol, start, end, x) + ".");
        break;
      case "portfolio-create": // Params: name
        name = sc.next();
        model.createPortfolio(name);
        view.displayMessage("Created portfolio " + name + ".");
        break;
      case "portfolio-add": // Params: name, symbol, amount
        name = sc.next();
        symbol = sc.next();
        amount = sc.nextInt();
        model.addStockToPortfolio(name, symbol, amount);
        view.displayMessage("Added " + amount + " of " + symbol + " to portfolio " + name + ".");
        break;
      case "portfolio-remove": // Params: name, symbol
        name = sc.next();
        symbol = sc.next();
        model.removeStockFromPortfolio(name, symbol);
        view.displayMessage("Removed " + symbol + " from portfolio " + name + ".");
        break;
      case "portfolio-value": // Params: name, date
        name = sc.next();
        date = LocalDate.parse(sc.next());
        view.displayMessage("Value of " + name + ": " + model.valueOfPortfolio(name, date) + ".");
        break;
      default:
        view.displayMessage("Undefined instruction: " + userInstruction + ".");
    }
  }

  private String menu() {
    String ls = System.lineSeparator();
    return "Dates must not have spaces and be parsable by LocalDate::parse" + ls + ls +
            "stock-examine-period [symbol] [start-date] [end-date]" + ls +
            "stock-examine-average [symbol] [date] [number of days]" + ls +
            "stock-examine-crossovers [symbol] [start-date] [end-date] [number of days]" + ls +
            "portfolio-create [name]" + ls +
            "portfolio-add [name] [symbol] [amount]" + ls +
            "portfolio-remove [name] [symbol]" + ls +
            "portfolio-value [name] [date]" + ls +
            "quit or q" + ls + ls +
            "Enter instruction: ";
  }
}
