package model;

import java.time.LocalDate;

class StockPurchase {
  final double amount;
  final LocalDate purchaseDate;

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
