package model;

import java.time.LocalDate;
import java.util.List;

public interface IStockBetterModel extends IStockModel {
  void createPortfolio(String name);

  void addStockToPortfolio(String name, String symbol, int amount, LocalDate date);

  void removeStockFromPortfolio(String name, String symbol, int amount, LocalDate date);

  BarGraph portfolioComposition(String name, LocalDate date);

  BarGraph portfolioDistribution(String name, LocalDate date);

  BarGraph stockPerformance(String symbol, LocalDate start, LocalDate end, int xLabelCount);

  BarGraph portfolioPerformance(String name, LocalDate start, LocalDate end, int xLabelCount);

  void portfolioRebalance(String name, List<Integer> split, LocalDate date);

  boolean hasPortfolio(String name);

  boolean isValidSplit(String name, List<String> split);
}
