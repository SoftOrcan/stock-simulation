package view;

/**
 * The StockView class is the interaction window between a user and the program. It is responsive
 * to the inputs of the user, and renders output messages from the controller (via the
 * 'displayMessage()' method).
 */
public class StockView implements IStockView {

  /**
   * Display a message from the controller.
   *
   * @param message The message from the controller to display.
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * Display a error from the controller.
   *
   * @param error The error from the controller to display.
   */
  public void displayError(String error) {
    System.out.println("Error: " + error);
  }
}
