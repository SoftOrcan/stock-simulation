package model;

import java.time.LocalDate;

class StockPurchase {
  final int amount;
  final LocalDate purchaseDate;

  StockPurchase(int amount, LocalDate purchaseDate) {
    this.amount = amount;
    this.purchaseDate = purchaseDate;
  }

  int getAmount() {
    return amount;
  }

  LocalDate getPurchaseDate() {
    return purchaseDate;
  }
}
