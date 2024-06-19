package controller;

import java.util.Map;

import javax.swing.*;

public interface Features {
  void quit();

  void enter(String action);

  void menu();

  void submit(String actionName, Map<String, String> entries);

  void validate(String actionName, String name, String value, JLabel label, JComponent entry);

  void validateDate(int day, int month, int year, JPanel dateBox);

  void setPortfolio(String portfolio);
}
