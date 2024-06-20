package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.*;

/**
 * The EntryPanel class prompts the user to enter the inputs, dependent on the feature selected.
 * For example, selecting "Create a portfolio" will prompt the user to enter the name for the
 * portfolio, while "Buy a Stock" will prompt the user to enter the stock symbol, the quantity of
 * shares, and the date of purchase.
 */
public class EntryPanel extends AStockPanel {
  /**
   * The EntryPanel class prompts the user to enter the inputs, dependent on the feature selected.
   * For example, selecting "Create a portfolio" will prompt the user to enter the name for the
   * portfolio, while "Buy a Stock" will prompt the user to enter the stock symbol, the quantity of
   * shares, and the date of purchase. This constructor creates the input boxes for the entries,
   * the input boxes dependent on which feature the user selected.
   *
   * @param actionName The feature the user selected.
   * @param inputNames The inputs required for the feature (except the date).
   * @param queryDates If this feature requires a date input.
   */
  public EntryPanel(String actionName, String[] inputNames, boolean queryDates) {
    super();
    setLayout(new GridLayout(0, 1));
    setName(actionName);

    this.add(new JLabel(actionName, SwingConstants.CENTER));
    JPanel entryPanel = new JPanel();
    entryPanel.setLayout(new GridLayout(0, 2));

    // Create an entry box for each entry.
    for (String name : inputNames) {
      JLabel label = new JLabel(name, SwingConstants.CENTER);
      label.setOpaque(true);
      entryPanel.add(label);
      JTextField textField = new JTextField();
      // Observes the keystrokes of the user, and records it in the text box.
      textField.addKeyListener(new KeyAdapter() {
        // Validate if the input is valid, depending on the type of input. For example, for stock
        // symbol, the program will validate if the symbol exists in the API.
        @Override
        public void keyReleased(KeyEvent e) {
          features.validate(actionName, name, textField.getText(), label, textField);
        }
      });
      entryPanel.add(textField);
    }

    // Add entry panel to query user input.
    if (inputNames.length != 0) {
      this.add(entryPanel);
    }

    // Add a dropdown for each date component.
    if (queryDates) {
      JPanel datePanel = new JPanel();
      datePanel.setLayout(new GridLayout(3, 2));

      JComboBox<Integer> years = new JComboBox<>();
      JComboBox<Integer> months = new JComboBox<>();
      JComboBox<Integer> days = new JComboBox<>();

      fillComboBox(years, 2000, 2024); // Add the years from 2000 to 2024.
      fillComboBox(months, 1, 12); // Add the months from January to December.
      fillComboBox(days, 1, 31); // Add the days from 1 to 31.

      // Ensure the date the user selected is a valid date.
      Action validateDate = new AbstractAction() {
        // Casting is required, as the 'getSelectedItem()' JavaSwing method returns an Object.
        @Override
        public void actionPerformed(ActionEvent e) {
          features.validateDate((int) Objects.requireNonNull(days.getSelectedItem()),
              (int) Objects.requireNonNull(months.getSelectedItem()),
              (int) Objects.requireNonNull(years.getSelectedItem()),
              datePanel);
        }
      };

      // Validate each date component.
      years.setAction(validateDate);
      months.setAction(validateDate);
      days.setAction(validateDate);

      // Add a label for the "Year" dropdown.
      datePanel.add(new JLabel("Year", SwingConstants.CENTER));
      datePanel.add(years);
      // Add a label for the "Month" dropdown.
      datePanel.add(new JLabel("Month", SwingConstants.CENTER));
      datePanel.add(months);
      // Add a label for the "Day" dropdown.
      datePanel.add(new JLabel("Day", SwingConstants.CENTER));
      datePanel.add(days);
      this.add(datePanel);

      // Create a submit button.
      JButton submit = new JButton(new AbstractAction("Submit") {
        /**
         * Record the entries from the user.
         *
         * @param event the event to be processed.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
          Map<String, String> entries = new HashMap<>();
          // Record the text entry components.
          for (int index = 0; index < inputNames.length; index++) {
            entries.put(inputNames[index],
                ((JTextField) entryPanel.getComponent(2 * index + 1)).getText());
          }
          // Record the date components.
          entries.put("year", String.valueOf(years.getSelectedItem()));
          entries.put("month", String.valueOf(months.getSelectedItem()));
          entries.put("day", String.valueOf(days.getSelectedItem()));
          // Submit the entries to evaluate in features.
          features.submit(actionName, entries);
        }
      });
      // Add a submit button.
      this.add(submit);
    } else {
      // If no entry date is required.
      JButton submit = new JButton(new AbstractAction("Submit") {
        /**
         * Record the entries from the user.
         *
         * @param event the event to be processed.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
          Map<String, String> entries = new HashMap<>();
          // Record the text entry components.
          for (int index = 0; index < inputNames.length; index++) {
            entries.put(inputNames[index],
                ((JTextField) entryPanel.getComponent(2 * index + 1)).getText());
          }
          // Submit the entries to evaluate in features.
          features.submit(actionName, entries);
        }
      });
      // Add a submit button.
      this.add(submit);
    }

    // Add the menu button to the GUI.
    this.add(new JButton(new AbstractAction("Menu") {
      // If this action is selected, reflect the selection in "features."
      @Override
      public void actionPerformed(ActionEvent e) {
        features.menu();
      }
    }));

    // Add the quit button to the GUI.
    this.add(quit);
  }

  private void fillComboBox(JComboBox<Integer> selection, int start, int end) {
    for (int value = start; value <= end; value += 1) {
      selection.addItem(value);
    }
  }
}
