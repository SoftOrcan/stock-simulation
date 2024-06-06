import org.junit.Before;
import org.junit.Test;

import model.AlphaVantageAPI;

public class AlphaVantageAPITest {
  AlphaVantageAPI api;

  @Before
  public void setUp() throws Exception {
    api = new AlphaVantageAPI();

  }

  @Test
  public void testAlphaVantageAPI() {
    api.getData("GOOG");
  }
}