import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    System.out.println(LocalDateTime.parse("2024-06-06 00:00:00", DateTimeFormatter.ofPattern(
        "yyyy-MM-dd HH:mm:ss")));
    // If the "data" folder does not exist, create it.
    File dataDirectory = new File("./data");
    if (!dataDirectory.exists()) {
      dataDirectory.mkdirs();
    }

    IStockModel model = new StockModel();
    IStockView view = new StockView();
    IStockController controller = new TextualStockController(model, view);
    controller.control();
  }
}
