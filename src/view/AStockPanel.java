package view;

import java.awt.event.ActionListener;

import javax.swing.*;

public abstract class AStockPanel extends JPanel {
  protected final JButton quit;

  public AStockPanel() {
    quit = new JButton("Quit");
    quit.setActionCommand("quit");
  }

  void addListener(ActionListener listener) {
    quit.addActionListener(listener);
  }
}
