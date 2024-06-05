package model;

public class StockPrice {
  String date;
  double openPrice;
  double closePrice;

  public StockPrice(String date, double openPrice, double closePrice) {
    this.date = date;
    this.openPrice = openPrice;
    this.closePrice = closePrice;
  }

}
