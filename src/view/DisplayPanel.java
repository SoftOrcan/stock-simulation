package view;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class DisplayPanel extends AStockPanel {

  public DisplayPanel(String message) {
    super();
    setLayout(new GridLayout(0, 1));
    setName("Display");

    add(new JLabel("<html>" + message.replace(System.lineSeparator(), "<br>") + "</html>",
        SwingConstants.CENTER));

    this.add(new JButton(new AbstractAction("Menu") {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.menu();
      }
    }));
    this.add(quit);
  }
}
