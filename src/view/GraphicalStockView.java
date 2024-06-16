package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

import javax.swing.*;

public class GraphicalStockView extends JFrame implements IStockView {
  private AStockPanel panel;
  private ActionListener listener;

  public GraphicalStockView() {
    setTitle("Stock Simulation");
    setSize(500, 500);
    this.panel = new ActionSelectionPanel();
    add(panel);
    setVisible(true);
  }

  public void addListener(ActionListener listener) {
    this.listener = listener;
    panel.addListener(listener);
  }

  @Override
  public void setPanel(AStockPanel panel) {
    remove(this.panel);
    this.panel = panel;
    panel.addListener(listener);
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
    for (Component component : panel.getComponents()) {
      if ("entry".equals(component.getName())) {
        for (Component child : ((JPanel) component).getComponents()) {
          if (name.equals(child.getName())) {
            return ((JTextField) child).getText();
          }
        }
      }
    }
    throw new IllegalStateException();
  }

  @Override
  public int getDropdown(String name) {
    for (Component component : panel.getComponents()) {
      if ("dropdown".equals(component.getName())) {
        for (Component child : ((JPanel) component).getComponents()) {
          if (name.equals(child.getName())) {
            return (int) Objects.requireNonNull(((JComboBox<?>) child).getSelectedItem());
          }
        }
      }
    }
    throw new IllegalStateException();
  }

  @Override
  public void quit() {
    this.dispose();
  }
}
