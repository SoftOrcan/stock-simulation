package model;

/**
 * The StockPrice class represents the prices of stocks at opening and closing times on a specified
 * date.
 */
class StockPrice {
  final double openPrice; // The opening price of the stock.
  final double closePrice; // The closing price of the stock.

  /**
   * Package-private constructor that permits the creation of a StockPrice, representing a stock's
   * opening and closing prices.
   *
   * @param openPrice  The opening price of the stock.
   * @param closePrice The closing price of the stock.
   */
  StockPrice(double openPrice, double closePrice) {
    this.openPrice = openPrice;
    this.closePrice = closePrice;
  }
}
