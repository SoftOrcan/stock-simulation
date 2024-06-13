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
   * @param amount       The amount of stock shares purchased.
   * @param purchaseDate The date of purchase.
   */
  StockPurchase(double amount, LocalDate purchaseDate) {
    this.amount = amount;
    this.purchaseDate = purchaseDate;
  }

  /**
   * Get the current quantity of shares of the stock. Since 'amount' is a primitive, this is a
   * permissible method, as the field cannot be modified.
   *
   * @return The quantity of shares of the stock.
   */
  double getAmount() {
    return amount;
  }

  /**
   * Get the purchase date of the stock. Since a LocalDate instance is immutable, this is a
   * permissible method.
   *
   * @return The purchase date of the stock.
   */
  LocalDate getPurchaseDate() {
    return purchaseDate;
  }
}
