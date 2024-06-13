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

/**
 * The BarGraph class represents a bar graph visualization, and contains methods to construct a bar
 * graph. Such that, a BarGraph is constructed via the 'createXAxis()', 'createYAxis()',
 * 'createStockYAxis()', and 'createPortfolioYAxis()' methods, in which the xAxis and yAxis will be
 * populated. The BarGraph is interpreted as a String via the 'interpret()' method for textual
 * display.
 */
public class BarGraph {
  private final String title; // The title of the bar graph.
  private final List<String> xAxis = new ArrayList<>(); // The x-axis elements.
  private final List<String> yAxis = new ArrayList<>(); // The y-axis elements.
  private String xInterval = ""; // The x-axis intervals.
  private String yScale = ""; // The y-axis intervals.
  // The StockDatabase to retrieve stock information from.
  private final StockDatabase stockDatabase = new StockDatabase();

  /**
   * Instantiates a new BarGraph with the provided title.
   *
   * @param title The title of the bar graph.
   */
  BarGraph(String title) {
    this.title = title;
  }

  /**
   * Interpret the BarGraph to a String for textual visualization purposes in StockView.
   *
   * @return String representation of a BarGraph.
   */
  public String interpret() {
    StringBuilder builder = new StringBuilder();
    builder.append(title); // Include the title.

    // Include the values in the x and y-axes.
    for (int i = 0; i < xAxis.size(); i += 1) {
      builder.append(System.lineSeparator())
          .append(xAxis.get(i))
          .append(": ")
          .append(yAxis.get(i));
    }

    // If there is an x-axis scale, include it in the String representation.
    if (!xInterval.isEmpty()) {
      builder.append(System.lineSeparator())
          .append("X-Axis Scale: Every ")
          .append(xInterval);
    }

    // If there is a y-axis scale, include it in the String representation.
    if (!yScale.isEmpty()) {
      builder.append(System.lineSeparator())
          .append("Y-Axis Scale: * = $")
          .append(yScale);
    }

    return builder.toString();
  }

  /**
   * Display the values in a portfolio on a specific date, the x-axis and y-axis values varying
   * depending on if the user is attempting to display the composition or distribution of a
   * portfolio.
   *
   * @param date          The date to examine.
   * @param portfolio     The portfolio to examine.
   * @param isComposition If attempting to display the composition or distribution of the portfolio.
   */
  void createEntries(LocalDate date, Set<Map.Entry<String, StockPurchase>> portfolio,
                     boolean isComposition) {
    // For every entry in the portfolio...
    for (Map.Entry<String, StockPurchase> entry : portfolio) {
      String stock = entry.getKey(); // Get the stock in the portfolio.
      StockPurchase purchase = entry.getValue(); // Get the purchaseDate of the stock.
      // Display only stocks with purchase dates up to the specified date.
      if (purchase.getPurchaseDate().isBefore(date) || purchase.getPurchaseDate().equals(date)) {
        // If the composition of a portfolio is requested,
        String yAxisValue = isComposition ?
            purchase.getAmount() + " shares." :
            "$" + stockDatabase.getPriceOn(stock, String.valueOf(date), "") * purchase.getAmount();
        xAxis.add(stock);
        yAxis.add(yAxisValue);
      }
    }
  }

  /**
   * Create x axis.
   *
   * @param start       the start
   * @param end         the end
   * @param xLabelCount the x label count
   */
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

  /**
   * Create stock y-axis.
   *
   * @param name  the name
   * @param start the start
   * @param end   the end
   */
  void createStockYAxis(String name, LocalDate start, LocalDate end) {
    List<Double> prices = getStockPrices(name, start, end);
    createYAxis(prices);
  }

  /**
   * Create portfolio y-axis.
   *
   * @param stocks the stocks
   * @param start  the start
   * @param end    the end
   */
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
