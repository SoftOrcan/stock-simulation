package controller;

import java.util.Map;

import javax.swing.*;

public interface Features {

  /**
   * Quit the stock simulation.
   */
  void quit();

  /**
   * Prompt user to enter inputs for given action.
   *
   * @param action The name of the intended action.
   */
  void enter(String action);

  /**
   * Display the menu.
   */
  void menu();

  /**
   * Do action. {@code entries} stores inputs from view.
   *
   * @param actionName The name of the intended action.
   * @param entries The map from field name to field value.
   */
  void submit(String actionName, Map<String, String> entries);

  /**
   * Notify user if {@code value} is invalid in relation to the given action.
   *
   * @param actionName The name of the intended action.
   * @param name The name of the field.
   * @param value The value of the field.
   * @param label The label panel used for field entry.
   * @param entry The input panel used for field entry.
   */
  void validate(String actionName, String name, String value, JLabel label, JComponent entry);

  /**
   * Notify user if provided date is invalid.
   *
   * @param day The given day.
   * @param month The given month.
   * @param year The given year.
   * @param dateBox The panel used for date entry.
   */
  void validateDate(int day, int month, int year, JPanel dateBox);

  /**
   * Set the portfolio to do operations on.
   *
   * @param portfolio The portfolio to select.
   */
  void setPortfolio(String portfolio);
}
