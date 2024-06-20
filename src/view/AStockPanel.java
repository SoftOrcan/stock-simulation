package view;

import java.awt.event.ActionEvent;

import javax.swing.*;

import controller.Features;

/**
 * The AStockPanel abstract class represents the functionality panels of the program. For example,
 * its child, "ActionSelectionPanel", provides a menu of the features of the program, while the
 * "EntryPanel" prompts the user to enter the inputs, dependent on the feature selected.
 */
abstract class AStockPanel extends JPanel {
  protected final JButton quit; // A panel must contain a quit button to terminate the program.
  protected Features features; // The additional functionalities on the panel.

  // Initializes the panel. A panel must contain the "Quit" button to allow the user to terminate
  // the program.
  AStockPanel() {
    quit = new JButton(new AbstractAction("Quit") {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.quit();
      }
    });
  }

  /**
   * Change the {@code Features} that events are sent to.
   *
   * @param features The {@code Features} to use.
   */
  protected void setFeatures(Features features) {
    this.features = features;
  }
}
