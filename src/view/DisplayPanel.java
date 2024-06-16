package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DisplayPanel extends AStockPanel {
  private final JButton menu;

  public DisplayPanel(String message) {
    super();
    setLayout(new GridLayout(0, 1));
    setName("Display");

    this.add(new JLabel(message));

    menu = new JButton("Menu");
    menu.setActionCommand("menu");
    this.add(menu);
    this.add(quit);
  }

  @Override
  void addListener(ActionListener listener) {
    super.addListener(listener);
    menu.addActionListener(listener);
  }
}
