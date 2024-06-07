import java.io.File;

import controller.IStockController;
import controller.TextualStockController;
import model.IStockModel;
import model.StockModel;
import view.IStockView;
import view.StockView;

public class RunStocksStimulation {
  public static void main(String[] args) {
    if (!new File("./data").exists()) {
      new File("./data").mkdirs();
    }
    IStockModel model = new StockModel();
    IStockView view = new StockView();
    IStockController controller = new TextualStockController(model, view);
    controller.control();
  }
}
