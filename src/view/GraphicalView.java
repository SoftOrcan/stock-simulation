package view;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

import javax.swing.*;

public class GraphicalView extends JFrame implements IStockView {
  private AStockPanel panel;

  public GraphicalView() {
    setTitle("Stock Simulation");
    setSize(500, 500);
    this.panel = new ActionSelectionPanel();
    add(panel);
    setVisible(true);
  }

  public void setListener(ActionListener listener) {
    panel.addListener(listener);
  }

  @Override
  public void setPanel(AStockPanel panel) {
    remove(this.panel);
    this.panel = panel;
    add(panel);
    setVisible(true);
  }

  @Override
  public void displayMessage(String message) {
    // do nothing
  }

  @Override
  public void displayError(String error) {
    JOptionPane.showConfirmDialog(null,
        error,
        "Error!",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public String getPanelName() {
    return panel.getName();
  }

  @Override
  public String getTextField(String name) {
    return ((JTextField) (Arrays.stream(panel.getComponents())
        .filter(c -> c instanceof JTextField && c.getName().equals(name + "Entry"))
        .findFirst()
        .orElseGet(JTextField::new)))
        .getText();
  }

  @Override
  public int getDropdown(String name) {
    return Objects.requireNonNull(((Integer) ((JComboBox<?>) (
        Arrays.stream(panel.getComponents())
            .filter(c -> c instanceof JComboBox && c.getName().equals(name + "Dropdown"))
            .findFirst()
            .orElseGet(JComboBox::new))).getSelectedItem()));
  }

  @Override
  public void quit() {
    this.dispose();
  }
}
