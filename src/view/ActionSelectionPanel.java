package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.*;

/**
 * The ActionSelectionPanel class provides a menu of the features of the program, such as the option
 * to create a portfolio, purchase a share of a stock to a portfolio, or compute the value of a
 * portfolio.
 */
public class ActionSelectionPanel extends AStockPanel {
  /**
   * The ActionSelectionPanel class provides a menu of the features of the program, such as the
   * option to create a portfolio, purchase a share of a stock to a portfolio, or compute the value
   * of a portfolio.
   *
   * @param portfolios The available portfolios for the user to select.
   * @param selected The portfolio the user selects.
   */
  public ActionSelectionPanel(List<String> portfolios, String selected) {
    super();
    setLayout(new GridLayout(0, 1));
    setName("ActionSelection");

    // Display the portfolios available for the user to select.
    this.add(new JLabel("Select portfolio:", SwingConstants.CENTER));
    JComboBox<String> portfolioBox = new JComboBox<>();

    for (String portfolio : portfolios) {
      portfolioBox.addItem(portfolio);
    }

    // If there are no existing portfolios, set the selected portfolio to empty.
    if (!selected.isEmpty()) {
      portfolioBox.setSelectedItem(selected);
    }

    portfolioBox.setAction(new AbstractAction() {
      @Override
      // Retrieve the portfolio the user selected from the JComboBox.
      public void actionPerformed(ActionEvent e) {
        // Casting is necessary here, as the 'getSelectedItem()' JavaSwing method returns an
        // Object.
        features.setPortfolio((String) portfolioBox.getSelectedItem());
      }
    });

    this.add(portfolioBox);

    // The available features of the program to add to the GUI...
    // Create a portfolio.
    this.add(new JButton(new AbstractAction("Create a Portfolio") {
      // If this action is selected, reflect the selection in "features."
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("create");
      }
    }));
    // Purchase a share of a stock to a portfolio.
    this.add(new JButton(new AbstractAction("Buy a Stock") {
      // If this action is selected, reflect the selection in "features."
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("add");
      }
    }));
    // Remove a share of a stock from a portfolio.
    this.add(new JButton(new AbstractAction("Sell a Stock") {
      // If this action is selected, reflect the selection in "features."
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("remove");
      }
    }));
    // Compute the value of a portfolio.
    this.add(new JButton(new AbstractAction("Value of Portfolio") {
      // If this action is selected, reflect the selection in "features."
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("value");
      }
    }));
    // Display the quantity of each stock in a portfolio.
    this.add(new JButton(new AbstractAction("Composition of Portfolio") {
      // If this action is selected, reflect the selection in "features."
      @Override
      public void actionPerformed(ActionEvent e) {
        features.enter("composition");
      }
    }));

    // Add the quit button to the GUI.
    this.add(quit);
  }
}
