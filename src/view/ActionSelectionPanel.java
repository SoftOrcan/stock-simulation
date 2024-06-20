package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.*;

public class ActionSelectionPanel extends AStockPanel {

  public ActionSelectionPanel(List<String> portfolios, String selected) {
    super();
    setLayout(new GridLayout(0, 1));
    setName("ActionSelection");

    this.add(new JLabel("Select portfolio:", SwingConstants.CENTER));
    JComboBox<String> portfolioBox = new JComboBox<>();
    for (String portfolio : portfolios) {
      portfolioBox.addItem(portfolio);
    }
    if (!selected.isEmpty()) {
      portfolioBox.setSelectedItem(selected);
      System.out.println(selected);
    }
    portfolioBox.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.setPortfolio((String) portfolioBox.getSelectedItem());
      }
    });
    this.add(portfolioBox);

    this.add(new JButton(new AbstractAction("Create a Portfolio") {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("create");
      }
    }));
    this.add(new JButton(new AbstractAction("Buy a Stock") {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("add");
      }
    }));
    this.add(new JButton(new AbstractAction("Sell a Stock") {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("remove");
      }
    }));
    this.add(new JButton(new AbstractAction("Value of Portfolio") {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("value");
      }
    }));
    this.add(new JButton(new AbstractAction("Composition of Portfolio") {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("composition");
      }
    }));
    this.add(quit);
  }
}
