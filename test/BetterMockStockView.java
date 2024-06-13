import java.util.ArrayList;
import java.util.List;


import view.IStockView;


public class BetterMockStockView implements IStockView {
  private final List<String> messages = new ArrayList<>();
  private final List<String> errors = new ArrayList<>();


  @Override
  public void displayMessage(String message) {
    messages.add(message);
  }


  @Override
  public void displayError(String error) {
    errors.add("Error: " + error);
  }


  public List<String> getMessages() {
    return messages;
  }


  public List<String> getErrors() {
    return errors;
  }
}
