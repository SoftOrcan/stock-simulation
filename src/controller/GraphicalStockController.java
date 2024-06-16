package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.function.Supplier;

import model.IStockBetterModel;
import view.ActionSelectionPanel;
import view.DisplayPanel;
import view.EntryPanel;
import view.IStockView;

public class GraphicalStockController implements IStockController, ActionListener {
  private final IStockView view;
  private final IStockBetterModel model;

  public GraphicalStockController(IStockBetterModel model, IStockView view) {
    this.model = model;
    this.view = view;
    view.addListener(this);
  }

  @Override
  public void control() {
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "quit":
        System.exit(0);
        break;
      // ActionSelection
      case "create":
        view.setPanel(new EntryPanel("create", new String[] {"name"}, false));
        break;
      case "add":
        view.setPanel(new EntryPanel("add", new String[] {"name", "symbol", "shares"}, true));
        break;
      case "remove":
        view.setPanel(new EntryPanel("remove", new String[] {"name", "symbol", "shares"}, true));
        break;
      case "value":
        view.setPanel(new EntryPanel("value", new String[] {"name"}, true));
        break;
      case "composition":
        view.setPanel(new EntryPanel("composition", new String[] {"name"}, true));
        break;
      case "submit":
        String name;
        String symbol;
        String shares;
        int day;
        int month;
        int year;
        LocalDate date;
        boolean isValid = true;
        Supplier<String> messageCreator;

        switch (view.getPanelName()) {
          case "create":
            name = view.getTextField("name");
            model.createPortfolio(name);
            messageCreator = () -> "Portfolio '" + name + "' created.";
            break;
          case "add":
            name = view.getTextField("name");
            isValid &= model.hasPortfolio(name);
            symbol = view.getTextField("symbol");
            isValid &= model.isValidSymbol(symbol);
            shares = view.getTextField("shares");
            isValid &= isValidInteger(shares);
            day = view.getDropdown("day");
            month = view.getDropdown("month");
            year = view.getDropdown("year");
            isValid &= validateDate(day, month, year);
            date = LocalDate.of(year, month, day);
            messageCreator =
                () -> {
                  model.addStockToPortfolioFile(name, symbol, Integer.parseInt(shares), date);
                  return "On " + date + ": Added '" + symbol + "' to Portfolio '" + name + "'.";
                };
            break;
          case "remove":
            name = view.getTextField("name");
            isValid &= model.hasPortfolio(name);
            symbol = view.getTextField("symbol");
            isValid &= model.isValidSymbol(symbol);
            shares = view.getTextField("shares");
            isValid &= isValidInteger(shares);
            day = view.getDropdown("day");
            month = view.getDropdown("month");
            year = view.getDropdown("year");
            isValid &= validateDate(day, month, year);
            date = LocalDate.of(year, month, day);
            messageCreator =
                () -> {
                  model.removeStockFromPortfolioFile(name, symbol, Integer.parseInt(shares), date);
                  return "On " + date + ": Removed '" + symbol + "' from Portfolio '" + name + "'.";
                };
            break;
          case "value":
            name = view.getTextField("name");
            isValid &= model.hasPortfolio(name);
            day = view.getDropdown("day");
            month = view.getDropdown("month");
            year = view.getDropdown("year");
            isValid &= validateDate(day, month, year);
            messageCreator = () -> "Value of '" + name + "': $"
                + model.portfolioValue(name, LocalDate.of(year, month, day));
            break;
          case "composition":
            name = view.getTextField("name");
            isValid &= model.hasPortfolio(name);
            day = view.getDropdown("day");
            month = view.getDropdown("month");
            year = view.getDropdown("year");
            isValid &= validateDate(day, month, year);
            messageCreator = () ->
                model.portfolioComposition(name, LocalDate.of(year, month, day)).interpret();
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + view.getPanelName());
        }
        if (isValid) {
          view.setPanel(new DisplayPanel(messageCreator.get()));
        } else {
          view.displayError("Invalid input given.");
        }
        break;
      case "menu":
        view.setPanel(new ActionSelectionPanel());
        break;
      default:
        throw new IllegalStateException("Unexpected command: " + e.getActionCommand());
    }
  }

  private boolean validateDate(int day, int month, int year) {
    try {
      LocalDate date = LocalDate.of(year, month, day);
      return date.isEqual(date);
    } catch (DateTimeException e) {
      return false;
    }
  }

  private boolean isValidInteger(String number) {
    try {
      int value = Integer.parseInt(String.valueOf(number));
      return value >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
