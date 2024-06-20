package view;

import javax.swing.*;

import controller.Features;

/**
 * The GraphicalStockView class is an interactive GUI between a user and the program. It is
 * responsive to the inputs of the user, and renders output messages from the controller (via the
 * 'displayMessage()' method). It draws the panel (via the 'draw()' method), and display the
 * available features of the program to the GUI (via the 'setFeatures()' method).
 */
public class GraphicalStockView extends JFrame implements IStockView {
  private AStockPanel panel; // The panel to display.
  private Features features; // The operations the panel is able to perform.

  /**
   * The GraphicalStockView class is an interactive GUI between a user and the program. It is
   * responsive to the inputs of the user, and renders output messages from the controller (via the
   * 'displayMessage()' method). It draws the panel (via the 'draw()' method), and display the
   * available features of the program to the GUI (via the 'setFeatures()' method).
   */
  public GraphicalStockView() {
    setTitle("Stock Simulation");
    setSize(500, 500);
    panel = new AStockPanel() {};
  }

  /**
   * Turn on the view.
   */
  @Override
  public void draw() {
    this.setVisible(true);
  }

  /**
   * Change the {@code Features} that events are sent to.
   *
   * @param features The {@code Features} to use.
   */
  public void setFeatures(Features features) {
    this.features = features;
    panel.setFeatures(features);
  }

  /**
   * Change the shown panel to the given panel.
   *
   * @param panel The new panel to display.
   */
  @Override
  public void setPanel(AStockPanel panel) {
    remove(this.panel); // Remove the current panel.
    this.panel = panel; // Set the current panel to the new panel.
    panel.setFeatures(features); // Set the features of the new panel.
    add(panel); // Add the panel.
    setVisible(true); // Display the panel.
  }

  /**
   * Display the message from the controller to the user.
   *
   * @param message The message from the controller to display.
   */
  @Override
  public void displayMessage(String message) {}

  /**
   * Display the error from the controller to the user.
   *
   * @param error The error from the controller to display.
   */
  @Override
  public void displayError(String error) {
    JOptionPane.showConfirmDialog(null,
        error,
        "Error!",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.ERROR_MESSAGE);
  }
}
