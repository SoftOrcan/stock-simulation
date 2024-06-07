import view.IStockView;

class MockStockView implements IStockView {
  private String lastDisplayedMessage;

  @Override
  public void displayMessage(String s) {
    // Store the last displayed message.
    lastDisplayedMessage = s;
  }

  /**
   * Retrieve the last displayed message for testing purposes.
   *
   * @return The last displayed message for testing purposes.
   */
  String getLastDisplayedMessage() {
    return lastDisplayedMessage;
  }
}