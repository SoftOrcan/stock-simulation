package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BarGraph {
  private final String title;
  private final List<String> xAxis = new ArrayList<>();
  private final List<String> yAxis = new ArrayList<>();
  private String xInterval = "";
  private String yScale = "";
  private final StockDatabase stockDatabase = new StockDatabase();

  BarGraph(String title) {
    this.title = title;
  }

  public String interpret() {
    StringBuilder builder = new StringBuilder();
    builder.append(title);

    for (int i = 0; i < xAxis.size(); i += 1) {
      builder.append(System.lineSeparator())
          .append(xAxis.get(i))
          .append(": ")
          .append(yAxis.get(i));
    }

    if (!xInterval.isEmpty()) {
      builder.append(System.lineSeparator())
          .append("X-Axis Scale: Every ")
          .append(xInterval);
    }

    if (!yScale.isEmpty()) {
      builder.append(System.lineSeparator())
          .append("Y-Axis Scale: * = $")
          .append(yScale);
    }

    return builder.toString();
  }

  void createEntries(LocalDate date, Set<Map.Entry<String, StockPurchase>> portfolio,
                     boolean isComposition) {
    for (Map.Entry<String, StockPurchase> entry : portfolio) {
      String stock = entry.getKey();
      StockPurchase purchase = entry.getValue();
      if (purchase.getPurchaseDate().isBefore(date) || purchase.getPurchaseDate().equals(date)) {
        String xAxisValue = isComposition ? stock : entry.getKey();
        String yAxisValue = isComposition ?
            purchase.getAmount() + " shares." :
            "$" + stockDatabase.getPriceOn(stock, String.valueOf(date), "") * purchase.getAmount();
        xAxis.add(xAxisValue);
        yAxis.add(yAxisValue);
      }
    }
  }

  void createXAxis(LocalDate start, LocalDate end, int xLabelCount) {
    int range = (int) ChronoUnit.DAYS.between(start, end);

    if (range < 5) {
      createIntervals(start, range, xLabelCount, "yyyy-MM-dd HH:mm:ss", 24);
    } else {
      createIntervals(start, range, xLabelCount, "yyyy-MM-dd", 1);
    }
  }

  private void createIntervals(LocalDate start, int range, int xLabelCount, String pattern,
                               int increment) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    int units = range * increment / (xLabelCount - 1);

    LocalDateTime curr = start.atStartOfDay();
    for (int i = 0; i < xLabelCount; i += 1) {
      xAxis.add(curr.format(formatter));
      curr = pattern.equals("yyyy-MM-dd") ? curr.plusDays(units) : curr.plusHours(units);
    }

    xInterval = units + (pattern.equals("yyyy-MM-dd") ? " day(s)" : " hours");
  }

  void createStockYAxis(String name, LocalDate start, LocalDate end) {
    List<Double> prices = getStockPrices(name, start, end);
    createYAxis(prices);
  }

  void createPortfolioYAxis(HashMap<String, Double> stocks, LocalDate start, LocalDate end) {
    boolean isShortInterval = ChronoUnit.DAYS.between(start, end) < 5;
    String intervalType = isShortInterval ? "60min" : "";
    List<Double> prices = new ArrayList<>();

    for (String interval : xAxis) {
      double total = 0;
      for (Map.Entry<String, Double> symbol : stocks.entrySet()) {
        total += symbol.getValue()
            * stockDatabase.getPriceOn(symbol.getKey(), interval, intervalType);
      }
      prices.add(total);
    }

    createYAxis(prices);
  }

  private List<Double> getStockPrices(String symbol, LocalDate start, LocalDate end) {
    boolean isShortInterval = ChronoUnit.DAYS.between(start, end) < 5;
    String intervalType = isShortInterval ? "60min" : "";

    List<Double> prices = new ArrayList<>();
    for (String interval : xAxis) {
      prices.add(stockDatabase.getPriceOn(symbol, interval, intervalType));
    }

    return prices;
  }

  private void createYAxis(List<Double> prices) {
    double max = Double.MIN_VALUE;
    for (Double number : prices) {
      max = Math.max(max, number);
    }

    double size = max / 50;
    while (size > 50) {
      size = size / 50;
    }

    for (Double price : prices) {
      yAxis.add("*".repeat((int) (price / size)));
    }

    yScale = String.valueOf(size);
  }
}
