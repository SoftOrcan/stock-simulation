import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import controller.Features;
import view.AStockPanel;
import view.IStockView;

/**
 * A mock implementation of the {@link IStockView} interface.
 * This class stores messages and errors that can be displayed in a UI.
 */
public class BetterMockStockView implements IStockView {
  private final List<String> messages = new ArrayList<>();
  private final List<String> errors = new ArrayList<>();

  /**
   * List to store messages.
   * @param message The message from the controller to display.
   */
  @Override
  public void displayMessage(String message) {
    messages.add(message);
  }

  /**
   * List to store errors.
   * @param error The error from the controller to display.
   */
  @Override
  public void displayError(String error) {
    errors.add("Error: " + error);
  }

  @Override
  public void setPanel(AStockPanel panel) {

  }

  @Override
  public void draw() {

  }

  @Override
  public void setFeatures(Features features) {

  }

  /**
   * Displays a message in the UI.
   * @return message The message to display.
   */
  public List<String> getMessages() {
    return messages;
  }

  /**
   * Displays an error message in the UI.
   * @return error The error message to display.
   */
  public List<String> getErrors() {
    return errors;
  }
}
