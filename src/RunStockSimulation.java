import java.io.File;
import java.io.InputStreamReader;

import controller.GraphicalStockController;
import controller.IStockController;
import controller.TextualStockBetterController;
import model.IStockBetterModel;
import model.StockBetterModel;
import view.GraphicalStockView;
import view.IStockView;
import view.StockView;

/**
 * The RunStockSimulation class runs the Stock Simulation program in its entirety, interacting the
 * IStockModel model, the IStockView view, and the IStockController controller to process, parse,
 * and display the user input(s) and output(s).
 */
public class RunStockSimulation {
  /**
   * The main method to run the Stock Simulation program by calling IStockController and displaying
   * the results textually.
   *
   * @param args The command line argument array.
   */
  public static void main(String[] args) throws IllegalArgumentException {
    // If the "data" directory does not exist, create it.
    File dataDirectory = new File("./data");
    if (!dataDirectory.exists()) {
      dataDirectory.mkdirs();
    }

    IStockBetterModel model = new StockBetterModel();
    IStockView view;
    IStockController controller;

    if (args.length == 0) {
      view = new GraphicalStockView();
      controller = new GraphicalStockController(model, view);
    } else if (args.length == 1 && args[0].equals("-text")) {
      view = new StockView(System.out);
      controller = new TextualStockBetterController(model, view, new InputStreamReader(System.in));
    } else {
      throw new IllegalStateException("Invalid command!");
    }

    controller.control();
  }
}
