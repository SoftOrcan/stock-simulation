package model;

import java.util.ArrayList;
import java.util.List;

public class BarGraph {
  final List<String> xAxis;
  final List<String> yAxis;
  final String title;
  final int scale;

  BarGraph(String title) {
    this.xAxis = new ArrayList<>();
    this.yAxis = new ArrayList<>();
    this.title = title;
    this.scale = 0;
  }

  public String interpret() {
    StringBuilder builder = new StringBuilder();
    builder.append(title);
    builder.append(System.lineSeparator());

    for (int i = 0; i < xAxis.size(); i++) {
      builder.append(xAxis.get(i));
      builder.append(": ");
      builder.append(yAxis.get(i));
      builder.append(System.lineSeparator());
    }

    if (scale != 0) {
      builder.append(scale);
    }

    return builder.toString();
  }
}
