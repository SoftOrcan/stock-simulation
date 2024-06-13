Re: Deliverables - Design Decision

IStockBetterModel (pt. 2)
Package "controller":


IStockModel (pt. 1)
Package "controller":
The "controller" package contains two files: the parent interface "IStockController" and
"TextualStockController", a class which implements IStockController. We created the interface in
case of future implementations where we might need to create another controller. In such a
scenario, we would simply need to create another class that implements IStockController, with
methods of a similar nature to TextualStockController. Since TextualStockController is accessed by
tests beyond the scope of the package, the class is required to be public.

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
