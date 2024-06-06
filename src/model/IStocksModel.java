package model;

import java.time.LocalDate;
import java.util.List;

public interface IStocksModel {
  double calculateChange(String symbol, LocalDate startDate, LocalDate endDate);

  double movingAverage(String symbol, LocalDate date, int numDays);

  List<LocalDate> findCrossovers(String symbol, LocalDate startDate, LocalDate endDate, int x);

  void createPortfolio(String portfolioName);

  void addStockToPortfolio(String portfolioName, String symbol, int amount);

  void removeStockFromPortfolio(String portfolioName, String symbol);

  double valueOfPortfolio(String portfolioName, LocalDate date);
}