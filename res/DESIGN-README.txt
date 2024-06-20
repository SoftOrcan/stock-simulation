Re: Deliverables - Design Decisions

IStockModel, IStockView & IStockController (pt. 1)
Package "controller":
The "controller" package contains four files: the parent interface "IStockController" and
"TextualStockController", a class which implements IStockController. We created the interface in
case of future implementations where we might need to create another controller. In such a
scenario, we would simply need to create another class that implements IStockController, with
methods of a similar nature to TextualStockController. Since TextualStockController is accessed by
tests beyond the scope of the package, the class is required to be public. The controller package
expands its functionality with the introduction of the TextualStockBetterController class, which
builds upon the foundation laid by TextualStockController while implementing the
IStockBetterController interface. This design choice was made to augment the stock
management system with advanced features while ensuring compatibility with existing
interfaces and future scalability. By inheriting from TextualStockController,
TextualStockBetterController leverages its core functionalities, maintaining code reusability and
adhering to object-oriented principles. The implementation of IStockBetterController extends the
controller's capabilities, enabling specialized operations tailored to more sophisticated stock
analysis and portfolio management tasks. This approach fosters clear separation of concerns and
facilitates seamless collaboration between the controller, IStockBetterModel, and IStockView,
ensuring cohesive interaction across application layers.

Package "view":
The "view" package shares a similar nature with the "controller" package. Therefore, we created a
parent interface (i.e., "IStockView") containing the methods required to render contents to the user
. This preparation is for future implementations in case we need to create another class with a
similar nature to StockView (the current class implementing IStockView), but it may contain methods
that behave differently from the current methods in StockView. Since StockView is accessed by
TextualStockController, the class is required to be public.

Package "model":
The "model" package contains the "backend" components of the Stock Simulation program. It includes
the primary public class "StockModel", which implements "IStockModel." This design decision shares a
 similar nature with the "controller" and "view" packages. This preparation is for future
 implementations in case we need to create another class with a similar nature to StockModel.
 However, the StockModel class delegates the implementation of the computations to other
 package-private classes and methods. Specifically, through the "AlphaVantageAPI" class, we were
 able to extract historical data for each stock symbol. The historical data is then parsed through
 the method "getHistoricalData()" in "StockDatabase", and represented as a "StockPrice." The
 computation is performed by package-private methods in StockInformation, which utilize the
 StockDatabase and the StockPrice classes to compute the necessary data. The class "Portfolio",
 similarly, is responsible for containing the implementations to create and modify a portfolio. The
 "backend" of the program, as a whole, delegates implementation to individual classes. The design
 decision to delegate responsibilities to different classes ensures that each component of the
 program handles specific tasks independently. By distributing functionalities across specialized
 classes, the program becomes more flexible, allowing for easier extension or modification of
 features in the future.

---

IStockBetterModel & IStockBetterController (pt. 2)
Package "controller":
The "controller" package contains an extension to the parent interface "IStockController," namely
"IStockBetterController." Although IStockBetterController does not contain any additional public
methods differing from IStockController, its implementing class, "TextualStockBetterController," is
capable of handling functionalities that its predecessor, "TextualStockController," cannot. This
includes the ability to perform "stock-performance," which visualizes the price of a stock over a
period of time, and "portfolio-composition" and "portfolio-distribution," which display the quantity
 and costs of each stock in the portfolio, respectively.

It is worthwhile to note that the implementation of IStockController has been modified.
Specifically, instead of requesting the user to specify a date within the LocalDate::parse format (i
.e., YYYY-MM-DD), the user is prompted to enter each date component individually. Thus, the user
will be prompted to enter the year, the month, and the date separately, and each component will be
validated individually. This approach extends to every user input component, such as entering the
portfolio name (via the 'validatePortfolio()' method), the amount of stock share to sell/add (via
the 'validateNatural()' and 'isValidInteger()' methods), and the stock symbol name (via the
'validateSymbol()' method). This design modification, aimed at validating every input separately,
was implemented to minimize user input errors.

Package "view":
The sole implementation modification is the addition of the Appendable 'out' field. This is an
improvement, as the previous implementation was tightly coupled with System.in and System.out.

Package "model":
An addition to the package is the interface "IStockBetterController" and its implementing class,
"StockBetterController". IStockBetterController extends the functionalities of IStockController.
This enables it to display portfolio performance (via the 'portfolioPerformance()' method),
portfolio distribution (i.e., the value of each stock in a portfolio) (via the
'portfolioDistribution()' method), portfolio composition (i.e., the quantity of each stock in a
portfolio) (via the 'portfolioComposition()' method), stock performance (via the 'stockPerformance()
' method), and rebalance a portfolio (i.e., modify the quantity of each stock based on provided
weights) (via the 'portfolioRebalance()' method). Furthermore, StockBetterController is able to
reflect modifications made to its HashMap directly in the .csv representation of a portfolio. Since
StockBetterController extends the functionalities of StockController, it is worthwhile to create an
interface that reflects its additional functionalities. However, it is still vital to preserve the
HashMap "portfolio", to ensure that the program does not rely simply on the .csv file to retrieve
data regarding a portfolio.

This follows with the Portfolio class. Since any modifications to the "portfolio" field must be
reflected to its .csv representation, it is worthwhile to create a class, namely "BetterPortfolio",
that is able to reflect these additional functionalities. The BetterPortfolio class contains methods
 that differ from its counterpart, "Portfolio". For example, the addition and removal of a stock
 requires a date, and it is able to rebalance, and visualize the composition and distribution of a
 portfolio.

Furthermore, to visualize the performance of a stock and portfolio over a time period, the class
"BarGraph" was implemented. The "backend" of the program, as a whole, delegates implementation to
individual classes. The design decision to delegate responsibilities to different classes ensures
that each component of the program handles specific tasks independently. By distributing
functionalities across specialized classes, the program becomes more flexible, allowing for easier
extension or modification of features in the future. Therefore, the "BarGraph" class is responsible
for the computation, interpretation, and visualization of a stock and portfolio performance, and
portfolio composition and distribution.

---

GraphicalStockController & GraphicalStockView (pt. 3)
Package "controller":
An inheriting child, named "GraphicalStockController", was added to the controller package. It is
responsible for processing the user interaction with the GUI. Specifically, the
GraphicalStockController is a GUI which receives and "parses" inputs by calling the appropriate,
corresponding method(s) in IStockBetterModel (via the 'control()' and 'enter()' methods). In
addition, the controller interacts with IStockView by displaying the output graphically, and
contains the method 'menu()' to display the available features of the program, the method 'quit()'
to terminate the program, and the methods 'enter()' and 'submit()' to process the user input and
return a result. Furthermore, the "Features" interface was implemented. The Features interface is
responsible for responding to the user interactions with the GUI. For example, opting to 'quit()'
would result in the termination of the program, while opting to view the menu (via the 'menu()'
method) would redirect the user to the selection page. Additional functionalities include the
ability to prompt the user to enter inputs for a given action (via the 'enter()' method), and
'submit()' the input(s) for the program to appropriately process. In a sense, the Features interface
serves as a connection between the controller and view packages.

Package "view":
A subclass to the IStockView, namely "GraphicalStockView", was implemented to reflect the
implementation of the GUI (versus the former text-based view). It is responsive to the inputs of the
user, and renders output messages from the controller (via the 'displayMessage()' method). It draws
the panel (via the 'draw()' method), and display the available features of the program to the GUI
(via the 'setFeatures()' method). Furthermore, to in support of GraphicalStockView, the abstract
class "AStockPanel" was implemented. The AStockPanel abstract class represents the functionality
panels of the program. For example, its child, "ActionSelectionPanel", provides a menu of the
features of the program, while the "EntryPanel" prompts the user to enter the inputs, dependent on
the feature selected. This means will allow the modification of panels and implementation of
additional panels to be smoother, as a class will simply need to be created that extends the
abstract class AStockPanel.

Package "model":
The only modifications made are in the StockBetterModel, permitting the methods to throw an
exception once an invalid input is provided. This does not change the implementation of the code,
but is rather, an enhancement. Suppose a different developer create a controller that uses the
present model, but does not validate the fields. By throwing an exception, the developer would be
aware to perform input validation in his/her controller implementation. Overall, this would create a
robust model that does not crash or depend on the controller to always provide it with validated
outputs.