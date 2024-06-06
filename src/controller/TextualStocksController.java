package controller;

import model.StocksModel;
import view.IStocksView;

import java.time.LocalDate;
import java.util.Scanner;

public class TextualStocksController implements IStocksController {
  private final IStocksView view;
  private final StocksModel model;

  public TextualStocksController(IStocksView view, StocksModel model) {
    this.view = view;
    this.model = model;
  }

  public void control() throws IllegalStateException {
    Scanner sc = new Scanner(System.in);
    // Print the welcome message
    view.displayMessage(this.menu());

    while (sc.hasNext()) { // Continue until the user quits
      view.displayMessage("Type instruction: " + System.lineSeparator()); // Prompt for the instruction name
      String userInstruction = sc.next(); // Take an instruction name
      if (userInstruction.equals("quit") || userInstruction.equals("q")) {
        break;
      } else {
        processCommand(userInstruction, sc, model);
      }
    }
  }

  private void processCommand(String userInstruction, Scanner sc, StocksModel model) {
    String symbol;
    LocalDate date;
    LocalDate start;
    LocalDate end;
    String name;
    int x;
    int amount;

    switch (userInstruction) {
      case "stock-examine-period": // symbol, start, end
        symbol = sc.next();
        start = LocalDate.parse(sc.next());
        end = LocalDate.parse(sc.next());
        model.calculateChange(symbol, start, end);
        break;
      case "stock-examine-average": // symbol, date, x
        symbol = sc.next();
        date = LocalDate.parse(sc.next());
        x = sc.nextInt();
        model.movingAverage(symbol, date, x);
        break;
      case "stock-examine-crossovers": // symbol, start, end, x
        symbol = sc.next();
        start = LocalDate.parse(sc.next());
        end = LocalDate.parse(sc.next());
        x = sc.nextInt();
        model.findCrossovers(symbol, start, end, x);
        break;
      case "portfolio-create": // portfolio name
        model.createPortfolio(sc.next());
        break;
      case "portfolio-add": // name, symbol, amount
        name = sc.next();
        symbol = sc.next();
        amount = sc.nextInt();
        model.addStockToPortfolio(name, symbol, amount);
        break;
      case "portfolio-remove": // name, symbol
        name = sc.next();
        symbol = sc.next();
        model.removeStockFromPortfolio(name, symbol);
        break;
      case "portfolio-value": // name, date
        name = sc.next();
        date = LocalDate.parse(sc.next());
        model.valueOfPortfolio(name, date);
        break;
      default:
        view.displayMessage("Undefined instruction: " + userInstruction + System.lineSeparator());
    }
  }

  protected String menu() {
    String ls = System.lineSeparator();

    return  "Dates must not have spaces and be parsable by LocalDate::parse" + ls +
            "stock-examine-period [symbol] [start-date] [end-date]" + ls +
            "stock-examine-average [symbol] [date] [number of days]" + ls +
            "stock-examine-crossovers [symbol] [start-date] [end-date] [number of days]" + ls +
            "portfolio-create [name]" + ls +
            "portfolio-add [name] [symbol] [amount]" + ls +
            "portfolio-remove [name] [symbol]" + ls +
            "portfolio-value [name] [date]" + ls;
  }

  // take in model and view

  // read/parse user input (similar to spreadsheet maybe?)

  // instead of print, call methods on view
}