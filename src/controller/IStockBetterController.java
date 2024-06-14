package controller;

/**
 * This was created for the class TextualStockBetterController, as it extends the functionalities in
 * TextualStockController.
 *
 * <p>The IStockController interface is responsible for interacting with the
 * IStockModel and IStockView. Specifically, it receives user input and calls the appropriate,
 * corresponding command in IStockModel. The output is displayed to the user via IStockView. These
 * actions are performed by the method 'control()'. The IStockBetterController interface
 * currently has no additional functionalities differing from IStockController, but it was
 * created in case extensions is necessary in future implementations.</p>
 */
public interface IStockBetterController extends IStockController {
}
