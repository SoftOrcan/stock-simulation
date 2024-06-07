import java.io.File;

import controller.IStockController;
import controller.TextualStockController;
import model.IStockModel;
import model.StockModel;
import view.IStockView;
import view.StockView;

/**
 * The RunStockSimulation class runs the Stock Simulation program in its entirety, interacting
 * the IStockModel model, the IStockView view, and the IStockController controller to process,
 * parse, and display the user input(s) and output(s).
 */
public class RunStockSimulation {
  /**
   * The main method to run the Stock Simulation program by calling IStockController and
   * displaying the results textually.
   *
   * @param args The command line argument array.
   */
  public static void main(String[] args) {
    // If the "data" folder does not exist, create it.
    if (!new File("./data").exists()) {
      new File("./data").mkdirs();
    }
    IStockModel model = new StockModel();
    IStockView view = new StockView();
    IStockController controller = new TextualStockController(model, view);
    controller.control();
  }
}
