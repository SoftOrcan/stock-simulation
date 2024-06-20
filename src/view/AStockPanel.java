package view;

import java.awt.event.ActionEvent;

import javax.swing.*;

import controller.Features;

/**
 * The AStockPanel abstract class
 */
abstract class AStockPanel extends JPanel {
  protected final JButton quit;
  protected Features features;

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
