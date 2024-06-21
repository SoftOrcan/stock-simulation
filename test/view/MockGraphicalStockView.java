package view;

import controller.Features;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;


/**
 * This class represents a mock representation of the Graphical Stock View.
 */
public class MockGraphicalStockView implements IStockView {
  private final List<String> messages = new ArrayList<>();
  private AStockPanel currentPanel;
  private Features features;

  /**
   * This method constructs a graphical stock view object.
   */
  public MockGraphicalStockView() {
    currentPanel = new MockPanel();
  }

  @Override
  public void draw() {
    messages.add("draw()");
  }

  @Override
  public void setPanel(AStockPanel panel) {
    this.currentPanel = panel;
    panel.setFeatures(features); // Ensure features are set when setting the panel
    messages.add("setPanel(" + panel.getName() + ")");
  }

  @Override
  public void displayMessage(String message) {
    messages.add("displayMessage(" + message + ")");
    features.enter(message);
  }

  @Override
  public void displayError(String error) {
    messages.add("displayError(" + error + ")");
  }

  @Override
  public void setFeatures(Features features) {
    this.features = features;
    currentPanel.setFeatures(features);
  }

  public List<String> getMessages() {
    return messages;
  }

  public Container getPanel() {
    return currentPanel;
  }

  public Component getCreatePanel() {
    return currentPanel;
  }

  /**
   * This class represents mock Panel to ensure getPanel() returns a non-null container
   */
  public class MockPanel extends AStockPanel {
    public MockPanel() {
      super();
      this.setName("MockPanel");
      this.setLayout(new GridLayout(0, 1));
      addButton("Create a Portfolio");
      addButton("Buy a Stock");
      addButton("Sell a Stock");
      addButton("Value of Portfolio");
      addButton("Composition of Portfolio");
      addTextField("name");
      addTextField("symbol");
      addTextField("shares");
      addDateFields();
      add(new JLabel("Output"));
      addButton("Submit"); // Add the Submit button to the panel
    }

    private void addButton(String text) {
      JButton button = new JButton(text);
      button.addActionListener(e -> features.enter(text));
      this.add(button);
    }

    private void addTextField(String name) {
      JLabel label = new JLabel(name);
      JTextField textField = new JTextField(10);
      textField.setName(name);
      this.add(label);
      this.add(textField);
    }

    private void addDateFields() {
      JComboBox<Integer> day = new JComboBox<>();
      JComboBox<Integer> month = new JComboBox<>();
      JComboBox<Integer> year = new JComboBox<>();
      day.setName("day");
      month.setName("month");
      year.setName("year");

      for (int i = 1; i <= 31; i++) {
        day.addItem(i);
      }
      for (int i = 1; i <= 12; i++) {
        month.addItem(i);
      }
      for (int i = 2000; i <= 2100; i++) {
        year.addItem(i);
      }

      this.add(new JLabel("Day"));
      this.add(day);
      this.add(new JLabel("Month"));
      this.add(month);
      this.add(new JLabel("Year"));
      this.add(year);
    }

    @Override
    public String getName() {
      return "MockPanel";
    }
  }

}


