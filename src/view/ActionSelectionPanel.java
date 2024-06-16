package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ActionSelectionPanel extends AStockPanel {
  private final JButton create;
  private final JButton add;
  private final JButton remove;
  private final JButton value;
  private final JButton composition;

  public ActionSelectionPanel() {
    super();
    setLayout(new GridLayout(0, 1));
    setName("ActionSelection");

    create = new JButton("Create a Portfolio");
    add = new JButton("Buy a Stock");
    remove = new JButton("Sell a Stock");
    value = new JButton("Value of Portfolio");
    composition = new JButton("Composition of Portfolio");

    create.setActionCommand("create");
    add.setActionCommand("add");
    remove.setActionCommand("remove");
    value.setActionCommand("value");
    composition.setActionCommand("composition");

    this.add(create);
    this.add(add);
    this.add(remove);
    this.add(value);
    this.add(composition);
    this.add(quit);
  }

  @Override
  void addListener(ActionListener listener) {
    super.addListener(listener);
    create.addActionListener(listener);
    add.addActionListener(listener);
    remove.addActionListener(listener);
    value.addActionListener(listener);
    composition.addActionListener(listener);
  }
}
