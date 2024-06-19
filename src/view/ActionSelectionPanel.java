package view;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class ActionSelectionPanel extends AStockPanel {

  public ActionSelectionPanel() {
    super();
    setLayout(new GridLayout(0, 1));
    setName("ActionSelection");

    JButton create = new JButton("Create a Portfolio");
    JButton add = new JButton("Buy a Stock");
    JButton remove = new JButton("Sell a Stock");
    JButton value = new JButton("Value of Portfolio");
    JButton composition = new JButton("Composition of Portfolio");

    create.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("create");
      }
    });
    add.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("add");
      }
    });
    remove.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("remove");
      }
    });
    value.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("value");
      }
    });
    composition.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("composition");
      }
    });

    this.add(create);
    this.add(add);
    this.add(remove);
    this.add(value);
    this.add(composition);
    this.add(quit);
  }
}
