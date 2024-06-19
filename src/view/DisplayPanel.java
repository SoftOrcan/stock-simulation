package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.Features;

public class DisplayPanel extends AStockPanel {

  public DisplayPanel(String message) {
    super();
    setLayout(new GridLayout(0, 1));
    setName("Display");

    JLabel label =
        new JLabel("<html>" + message.replace(System.lineSeparator(), "<br>") + "</html>");
    add(label);

    JButton menu = new JButton("Menu");
    menu.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.menu();
      }
    });
    this.add(menu);
    this.add(quit);
  }
}
