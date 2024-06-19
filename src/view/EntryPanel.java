package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.*;

public class EntryPanel extends AStockPanel {

  public EntryPanel(String actionName, String[] inputNames, boolean queryDates) {
    super();
    setLayout(new GridLayout(0, 1));
    setName(actionName);

    this.add(new JLabel(actionName));
    JPanel entryPanel = new JPanel();
    entryPanel.setLayout(new GridLayout(0, 2));

    for (String name : inputNames) {
      JLabel label = new JLabel(name);
      entryPanel.add(label);
      JTextField textField = new JTextField();
      textField.setAction(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          features.validate(actionName, name, textField.getText(), label, textField);
        }
      });
      entryPanel.add(textField);
    }

    this.add(entryPanel);

    if (queryDates) {
      JPanel datePanel = new JPanel();

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

      datePanel.add(new JLabel("Year"));
      datePanel.add(years);
      datePanel.add(new JLabel("Month"));
      datePanel.add(months);
      datePanel.add(new JLabel("Day"));
      datePanel.add(days);
      this.add(datePanel);


    }


    JButton submit = new JButton("Submit");
    submit.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Map<String, String> entries = new HashMap<>();
        for (int index = 0; index < inputNames.length; index++) {
          entries.put(inputNames[index],
              ((JTextField) entryPanel.getComponent(2 * index + 1)).getText());
        }
        if (queryDates) {
          entries.put("year",
              String.valueOf(((JComboBox<?>) ((JPanel) getComponent(2)).getComponent(1)).getSelectedItem()));
          entries.put("month",
              String.valueOf(((JComboBox<?>) ((JPanel) getComponent(2)).getComponent(3)).getSelectedItem()));
          entries.put("day",
              String.valueOf(((JComboBox<?>) ((JPanel) getComponent(2)).getComponent(5)).getSelectedItem()));
        }
        features.submit(actionName, entries);
      }
    });

    this.add(submit);
    this.add(quit);
  }

  private void fillComboBox(JComboBox<Integer> selection, int start, int end) {
    for (int value = start; value <= end; value += 1) {
      selection.addItem(value);
    }
  }
}
