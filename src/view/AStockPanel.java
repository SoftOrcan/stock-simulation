package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.Features;

public abstract class AStockPanel extends JPanel {
  protected final JButton quit;
  protected Features features;

  public AStockPanel() {
    quit = new JButton(new AbstractAction("Quit") {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.quit();
      }
    });
  }

  protected void setFeatures(Features features) {
    this.features = features;
  }
}
