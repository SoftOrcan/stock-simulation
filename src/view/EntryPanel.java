package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EntryPanel extends AStockPanel {
  private final JPanel entryPanel;
  private final JButton submit;

  public EntryPanel(String actionName, String[] inputNames, boolean queryDates) {
    super();
    setLayout(new GridLayout(0, 1));
    setName(actionName);

    entryPanel = new JPanel();
    submit = new JButton("Submit");
    setUp(actionName, inputNames, queryDates);
  }

  private void setUp(String actionName, String[] inputNames, boolean queryDates) {
    this.add(new JLabel(actionName));
    entryPanel.setLayout(new GridLayout(0, 2));

    for (String name : inputNames) {
      entryPanel.add(new JLabel(name));
      JTextField textField = new JTextField();
      textField.setName(name + "Entry");
      entryPanel.add(new JTextField());
    }

    this.add(entryPanel);

    if (queryDates) {
      JComboBox<Integer> years = new JComboBox<>();
      JComboBox<Integer> months = new JComboBox<>();
      JComboBox<Integer> days = new JComboBox<>();

      fillComboBox(years, 2000, 2024);
      fillComboBox(months, 1, 12);
      fillComboBox(days, 1, 31);

      years.setName("yearDropdown");
      months.setName("monthDropdown");
      days.setName("dayDropdown");

      JPanel dates = new JPanel();
      dates.setLayout(new GridLayout(0, 2));
      dates.add(new JLabel("Year"));
      dates.add(years);
      dates.add(new JLabel("Month"));
      dates.add(months);
      dates.add(new JLabel("Day"));
      dates.add(days);
      this.add(dates);
    }

    submit.setActionCommand("submit");
    this.add(submit);
    this.add(quit);
  }

  private void fillComboBox(JComboBox<Integer> selection, int start, int end) {
    for (int value = start; value <= end; value += 1) {
      selection.addItem(value);
    }
  }

  @Override
  void addListener(ActionListener listener) {
    super.addListener(listener);
    submit.addActionListener(listener);
  }
}
