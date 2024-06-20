package view;

import java.awt.event.ActionEvent;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.AbstractAction;

/**
 * The DisplayPanel class presents the result of the program to the user, and offers the user the
 * option to continue the program (by returning to the menu), or terminate the program by quitting.
 */
public class DisplayPanel extends AStockPanel {
  /**
   * The DisplayPanel class presents the result of the program to the user, and offers the user the
   * option to continue the program (by returning to the menu), or terminate the program by
   * quitting.
   *
   * @param message The result of the computation from the program.
   */
  public DisplayPanel(String message) {
    super();
    setLayout(new GridLayout(0, 1));
    setName("Display");

    // Line separator, to ensure that Strings containing line separators are properly reflected
    // in the GUI.
    add(new JLabel("<html>" + message.replace(System.lineSeparator(), "<br>") + "</html>",
        SwingConstants.CENTER));

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
}
