package view;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


import controller.Features;
import view.AStockPanel;
import view.IStockView;


/**
 * The MockStockView class is a mock for IStockView.
 */
public class MockStockView implements IStockView {
  private final List<String> messages;
  private final List<String> errors;


  public MockStockView() {
    messages = new ArrayList<>();
    errors = new ArrayList<>();
  }

  @Override
  public void displayMessage(String message) {
    // Store the last displayed message.
    messages.add(message);
  }

  @Override
  public void displayError(String error) {
    errors.add(error);
  }

  @Override
  public void setPanel(AStockPanel panel) {
    String string = null;
  }

  @Override
  public void draw() {
    String string = null;
  }

  @Override
  public void setFeatures(Features features) {
    String string = null;
  }

  /**
   * Retrieve list of messages in order of receipt for testing purposes.
   *
   * @return The list of messages
   */
  public List<String> getMessages() {
    return messages;
  }


  /**
   * Retrieve list of errors in order of receipt for testing purposes.
   *
   * @return The list of errors
   */
  public List<String> getErrors() {
    return errors;
  }
}
