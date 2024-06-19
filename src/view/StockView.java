package view;

import java.io.IOException;
import java.util.List;

import controller.Features;

/**
 * The StockView class is the interaction window between a user and the program. It is responsive
 * to the inputs of the user, and renders output messages from the controller (via the
 * 'displayMessage()' method).
 */
public class StockView implements IStockView {
  private final Appendable out; // The message to display.

  /**
   * The StockView class is the interaction window between a user and the program. It is responsive
   * to the inputs of the user, and renders output messages from the controller (via the
   * 'displayMessage()' method).
   *
   * @param out The message to display.
   */
  public StockView(Appendable out) {
    this.out = out;
  }

  /**
   * Display a message from the controller.
   *
   * @param message The message from the controller to display.
   */
  public void displayMessage(String message) {
    writeOutWithNewline(message);
  }

  /**
   * Display an error from the controller.
   *
   * @param error The error from the controller to display.
   */
  public void displayError(String error) {
    writeOutWithNewline("Error: " + error);
  }

  public void setFeatures(Features features) {
    // does nothing, used for GUI
  }

  public void setPanel(AStockPanel panel) {
    // does nothing, used for GUI
  }

  @Override
  public void draw() {
    // does nothing, used for GUI
  }

  /**
   * Display the message as a new line.
   *
   * @param message The message to display.
   */
  private void writeOutWithNewline(String message) {
    try {
      out.append(message).append("\n");
    } catch (IOException ex) {
      throw new IllegalStateException("IOException on writing out stream. Something bad happened!");
    }
  }
}
