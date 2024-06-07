import java.util.ArrayList;
import java.util.List;

import view.IStockView;

class MockStockView implements IStockView {
  private final List<String> messages;
  private final List<String> errors;

  MockStockView() {
    messages = new ArrayList<>();
    errors = new ArrayList<>();
  }

  public void displayMessage(String message) {
    // Store the last displayed message.
    messages.add(message);
  }

  public void displayError(String error) {
    errors.add(error);
  }

  /**
   * Retrieve list of messages in order of receipt for testing purposes.
   *
   * @return The list of messages
   */
  List<String> getMessages() {
    return messages;
  }

  /**
   * Retrieve list of errors in order of receipt for testing purposes.
   *
   * @return The list of errors
   */
  List<String> getErrors() {
    return errors;
  }
}