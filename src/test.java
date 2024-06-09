import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class test {
  public static void main(String[] args) {
    LocalDate start = LocalDate.parse("2023-06-06");
    LocalDate end = LocalDate.parse("2024-06-06");
    int size = 5;
    long numOfDaysBetween = ChronoUnit.DAYS.between(start, end);
    Collection<LocalDate> out = IntStream.iterate(0, i -> i + 1)
            .limit(numOfDaysBetween)
            .mapToObj(start::plusDays)
            .collect(Collectors.toList());

  }
}
