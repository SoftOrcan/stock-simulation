package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.*;

public class EntryPanel extends AStockPanel {

  public EntryPanel(String actionName, String[] inputNames, boolean queryDates) {
    super();
    setLayout(new GridLayout(0, 1));
    setName(actionName);

    this.add(new JLabel(actionName, SwingConstants.CENTER));
    JPanel entryPanel = new JPanel();
    entryPanel.setLayout(new GridLayout(0, 2));

    for (String name : inputNames) {
      JLabel label = new JLabel(name, SwingConstants.CENTER);
      label.setOpaque(true);
      entryPanel.add(label);
      JTextField textField = new JTextField();
      textField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
          features.validate(actionName, name, textField.getText(), label, textField);
          System.out.println(e.getKeyChar());
        }
      });
      entryPanel.add(textField);
    }

    if (inputNames.length != 0) {
      this.add(entryPanel);
    }

    if (queryDates) {
      JPanel datePanel = new JPanel();
      datePanel.setLayout(new GridLayout(3, 2));

      JComboBox<Integer> years = new JComboBox<>();
      JComboBox<Integer> months = new JComboBox<>();
      JComboBox<Integer> days = new JComboBox<>();

      fillComboBox(years, 2000, 2024);
      fillComboBox(months, 1, 12);
      fillComboBox(days, 1, 31);

      Action validateDate = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          features.validateDate((int) Objects.requireNonNull(days.getSelectedItem()),
              (int) Objects.requireNonNull(months.getSelectedItem()),
              (int) Objects.requireNonNull(years.getSelectedItem()),
              datePanel);
        }
      };
      years.setAction(validateDate);
      months.setAction(validateDate);
      days.setAction(validateDate);

      datePanel.add(new JLabel("Year", SwingConstants.CENTER));
      datePanel.add(years);
      datePanel.add(new JLabel("Month", SwingConstants.CENTER));
      datePanel.add(months);
      datePanel.add(new JLabel("Day", SwingConstants.CENTER));
      datePanel.add(days);
      this.add(datePanel);

      JButton submit = new JButton(new AbstractAction("Submit") {
        @Override
        public void actionPerformed(ActionEvent e) {
          Map<String, String> entries = new HashMap<>();
          for (int index = 0; index < inputNames.length; index++) {
            entries.put(inputNames[index],
                ((JTextField) entryPanel.getComponent(2 * index + 1)).getText());
          }
          entries.put("year",
              String.valueOf(years.getSelectedItem()));
          entries.put("month",
              String.valueOf(months.getSelectedItem()));
          entries.put("day",
              String.valueOf(days.getSelectedItem()));
          features.submit(actionName, entries);
        }
      });
      this.add(submit);
    } else {
      JButton submit = new JButton(new AbstractAction("Submit") {
        @Override
        public void actionPerformed(ActionEvent e) {
          Map<String, String> entries = new HashMap<>();
          for (int index = 0; index < inputNames.length; index++) {
            entries.put(inputNames[index],
                ((JTextField) entryPanel.getComponent(2 * index + 1)).getText());
          }
          features.submit(actionName, entries);
        }
      });
      this.add(submit);
    }



    this.add(new JButton(new AbstractAction("Menu") {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.menu();
      }
    }));
    this.add(quit);
  }

  private void fillComboBox(JComboBox<Integer> selection, int start, int end) {
    for (int value = start; value <= end; value += 1) {
      selection.addItem(value);
    }
  }
}
