package model;

import java.time.LocalDate;

/**
 * The StockPurchase class represents a purchase of a share of stock, containing the amount
 * purchased and the date of purchase.
 */
class StockPurchase {
  private final double amount; // The amount of stock shares purchased.
  private final LocalDate purchaseDate; // The date of purchase.

  /**
   * The StockPurchase class represents a purchase of a share of stock, containing the amount
   * purchased and the date of purchase.
   *
   * @param amount The amount of stock shares purchased.
   * @param purchaseDate The date of purchase.
   */
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
