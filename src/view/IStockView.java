package view;

import java.awt.event.ActionListener;

import javax.swing.*;

import controller.GraphicalStockController;

/**
 * The IStockView interface is responsible for displaying the output of the user interaction (via
 * the 'displayMessage()' method). It interacts with the controller to render information to the
 * user interface following the user input.
 */
public interface IStockView {

  /**
   * Display the message from the controller to the user.
   *
   * @param message The message from the controller to display.
   */
  void displayMessage(String message);

  /**
   * Display the error from the controller to the user.
   *
   * @param error The error from the controller to display.
   */
  void displayError(String error);

  void setPanel(AStockPanel panel);

  String getPanelName();

  String getTextField(String name);

  int getDropdown(String name);

  void quit();

  void addListener(ActionListener listener);
}