package controller;

import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.*;

import model.IStockBetterModel;
import view.ActionSelectionPanel;
import view.DisplayPanel;
import view.EntryPanel;
import view.IStockView;

public class GraphicalStockController implements IStockController, Features {
  private final IStockView view;
  private final IStockBetterModel model;
  private String selected;

  public GraphicalStockController(IStockBetterModel model, IStockView view) {
    this.model = model;
    this.view = view;
    selected = "";
  }

  @Override
  public void control() {
    List<String> portfolios = model.getPortfolios();
    selected = portfolios.isEmpty() ? "" :
        portfolios.get(0);
    view.setPanel(new ActionSelectionPanel(portfolios, selected));
    view.setFeatures(this);
  }

  @Override
  public void enter(String action) {
    if (action.equals("create")) {
      view.setPanel(new EntryPanel("create", new String[]{"name"}, false));
      return;
    }
    if (!selected.isEmpty()) {
      switch (action) {
        case "add":
          view.setPanel(new EntryPanel("add", new String[]{"symbol", "shares"}, true));
          break;
        case "remove":
          view.setPanel(new EntryPanel("remove", new String[]{"symbol", "shares"}, true));
          break;
        case "value":
          view.setPanel(new EntryPanel("value", new String[]{}, true));
          break;
        case "composition":
          view.setPanel(new EntryPanel("composition", new String[]{}, true));
          break;
        default:
          throw new IllegalStateException("Invalid action: " + action);
      }
    } else {
      view.displayError("Please create or select a profile");
    }
  }

  @Override
  public void submit(String actionName, Map<String, String> entries) {
    String name;
    String symbol;
    String shares;
    int day;
    int month;
    int year;
    boolean allValid;
    Supplier<String> messageCreator;

    switch (actionName) {
      case "create":
        name = entries.get("name");
        allValid = !name.isEmpty();
        messageCreator = () -> {
          model.createPortfolio(name);
          setPortfolio(name);
          return "Portfolio '" + name + "' created.";
        };
        break;
      case "add":
        symbol = entries.get("symbol");
        allValid = model.isValidSymbol(symbol);
        shares = entries.get("shares");
        allValid &= isValidInteger(shares);
        day = Integer.parseInt(entries.get("day"));
        month = Integer.parseInt(entries.get("month"));
        year = Integer.parseInt(entries.get("year"));
        allValid &= isValidDate(day, month, year);
        messageCreator =
            () -> {
              LocalDate date = LocalDate.of(year, month, day);
              model.addStockToPortfolioFile(selected, symbol, Integer.parseInt(shares), date);
              return "On " + date + ": Added '" + symbol + "' to Portfolio '" + selected + "'.";
            };
        break;
      case "remove":
        symbol = entries.get("symbol");
        allValid = model.isValidSymbol(symbol);
        shares = entries.get("shares");
        allValid &= isValidInteger(shares);
        day = Integer.parseInt(entries.get("day"));
        month = Integer.parseInt(entries.get("month"));
        year = Integer.parseInt(entries.get("year"));
        allValid &= isValidDate(day, month, year);
        messageCreator =
            () -> {
              LocalDate date = LocalDate.of(year, month, day);
              model.removeStockFromPortfolioFile(selected, symbol, Integer.parseInt(shares), date);
              return "On " + date + ": Removed '" + symbol + "' from Portfolio '" + selected + "'.";
            };
        break;
      case "value":
        day = Integer.parseInt(entries.get("day"));
        month = Integer.parseInt(entries.get("month"));
        year = Integer.parseInt(entries.get("year"));
        allValid = isValidDate(day, month, year);
        messageCreator = () -> "Value of '" + selected + "': $"
            + model.portfolioValue(selected, LocalDate.of(year, month, day));
        break;
      case "composition":
        day = Integer.parseInt(entries.get("day"));
        month = Integer.parseInt(entries.get("month"));
        year = Integer.parseInt(entries.get("year"));
        allValid = isValidDate(day, month, year);
        messageCreator = () ->
            model.portfolioComposition(selected, LocalDate.of(year, month, day)).interpret();
        break;
      default:
        throw new IllegalStateException("Invalid action: " + actionName);
    }
    if (allValid) {
      view.setPanel(new DisplayPanel(messageCreator.get()));
    } else {
      view.displayError("Invalid input provided.");
    }
  }

  @Override
  public void validate(String actionName, String name, String value, JLabel label,
                       JComponent entry) {
    switch (name) {
      case "name":
        if (actionName.equals("create")) {
          changeValidation(label, entry, !value.isEmpty());
        } else {
          changeValidation(label, entry, model.hasPortfolio(value));
        }
        break;
      case "symbol":
        changeValidation(label, entry, model.isValidSymbol(value));
        break;
      case "shares":
        changeValidation(label, entry, isValidInteger(value));
        break;
    }
  }

  @Override
  public void validateDate(int day, int month, int year, JPanel dateBox) {
    if (isValidDate(day, month, year)) {
      dateBox.setBackground(Color.GREEN);
    } else {
      dateBox.setBackground(Color.RED);
    }
  }

  @Override
  public void setPortfolio(String portfolio) {
    selected = portfolio;
  }

  @Override
  public void menu() {
    view.setPanel(new ActionSelectionPanel(model.getPortfolios(), selected));
  }

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
