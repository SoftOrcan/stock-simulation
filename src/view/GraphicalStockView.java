package view;

import javax.swing.*;

import controller.Features;

public class GraphicalStockView extends JFrame implements IStockView {
  private AStockPanel panel;
  private Features features;

  public GraphicalStockView() {
    setTitle("Stock Simulation");
    setSize(500, 500);
    panel = new AStockPanel() {}; // dud panel
  }

  @Override
  public void draw() {
    this.setVisible(true);
  }

  public void setFeatures(Features features) {
    this.features = features;
    panel.setFeatures(features);
  }

  @Override
  public void setPanel(AStockPanel panel) {
    remove(this.panel);
    this.panel = panel;
    panel.setFeatures(features);
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
}
