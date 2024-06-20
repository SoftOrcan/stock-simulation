package view;

import controller.Features;

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

  /**
   * Change the shown panel to the given panel.
   *
   * @param panel The new panel to display.
   */
  void setPanel(AStockPanel panel);

  /**
   * Turn on the view.
   */
  void draw();

  /**
   * Change the {@code Features} that events are sent to.
   *
   * @param features The {@code Features} to use.
   */
  void setFeatures(Features features);
}