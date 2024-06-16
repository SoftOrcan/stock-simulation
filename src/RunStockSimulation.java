import java.io.File;

import controller.GraphicalStockController;
import controller.IStockController;
import model.IStockBetterModel;
import model.StockBetterModel;
import view.GraphicalStockView;
import view.IStockView;

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
    // If the "data" directory does not exist, create it.
    File dataDirectory = new File("./data");
    if (!dataDirectory.exists()) {
      dataDirectory.mkdirs();
    }

    IStockBetterModel model = new StockBetterModel();
    IStockView view = new GraphicalStockView();
    IStockController controller = new GraphicalStockController(model, view);
    controller.control();
  }
}
