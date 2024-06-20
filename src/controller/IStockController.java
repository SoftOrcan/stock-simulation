package controller;

/**
 * The IStockController interface is responsible for interacting with the IStockModel and
 * IStockView. Specifically, it receives user input and calls the appropriate, corresponding command
 * in IStockModel. The output is displayed to the user via IStockView. These actions are performed
 * by the method 'control()'.
 */
public interface IStockController {
  /**
   * Display the menu screen containing controls to the user. Receive user input and call the
   * appropriate, corresponding method in IStockModel. Display the output to the user via
   * IStockView.
   */
  void control();
}