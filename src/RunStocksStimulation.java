import java.io.File;

import controller.IStocksController;
import controller.TextualStocksController;
import model.IStocksModel;
import model.StocksModel;
import view.IStocksView;
import view.StocksView;

public class RunStocksStimulation {
  public static void main(String[] args) {
    if (!new File("./data").exists()) {
      new File("./data").mkdirs();
    }
    IStocksModel model = new StocksModel();
    IStocksView view = new StocksView();
    IStocksController controller = new TextualStocksController(model, view);
    controller.control();
  }
}
