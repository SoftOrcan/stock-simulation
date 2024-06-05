package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Interpreter implements IStocksModel {

  private String symbol;
  private List<StockPrice> prices;

  public Interpreter(String symbol) {
    this.symbol = symbol;
    this.prices = new ArrayList<>();
    getHistoricalPrices(symbol + ".csv");
  }

  private void getHistoricalPrices(String symbol) {
    try (BufferedReader br = new BufferedReader(new FileReader(symbol))) {
      String line;
      br.readLine(); // Skip header
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        String date = values[0];
        double open = Double.parseDouble(values[1]);
        double close = Double.parseDouble(values[4]);
        prices.add(new StockPrice(date, open, close));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // method (haven't been implemented)

  protected double calculateChange(String startDate, String endDate) {
    return 0;
  }

  protected double movingAverage(String day) {
    return 0;
  }

  protected List<String> findCrossovers(int x) {
    return null;
  }

}