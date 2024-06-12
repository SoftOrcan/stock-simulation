package model;

import java.time.LocalDate;

class StockPurchase {
  private final double amount;
  private final LocalDate purchaseDate;

  StockPurchase(double amount, LocalDate purchaseDate) {
    this.amount = amount;
    this.purchaseDate = purchaseDate;
  }

  double getAmount() {
    return amount;
  }

  LocalDate getPurchaseDate() {
    return purchaseDate;
  }
}
