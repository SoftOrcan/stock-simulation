import java.io.File;
import java.io.InputStreamReader;

import controller.IStockBetterController;
import controller.IStockController;
import controller.TextualStockBetterController;
import controller.TextualStockController;
import model.IStockBetterModel;
import model.IStockModel;
import model.StockBetterModel;
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
    // If the "data" directory does not exist, create it.
    File dataDirectory = new File("./data");
    if (!dataDirectory.exists()) {
      dataDirectory.mkdirs();
    }

    IStockBetterModel model = new StockBetterModel();
    IStockView view = new StockView(System.out);
    IStockBetterController controller = new TextualStockBetterController(model, view,
        new InputStreamReader(System.in));
    controller.control();
  }
}
