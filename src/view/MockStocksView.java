package view;

public class MockStocksView implements IStocksView {

  private String lastDisplayedMessage;

  @Override
  public void displayMessage(String s) {
    // Store the last displayed message
    lastDisplayedMessage = s;
  }

  // Getter method to retrieve the last displayed message for testing
  public String getLastDisplayedMessage() {
    return lastDisplayedMessage;
  }
}