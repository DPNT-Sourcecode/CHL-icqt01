package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class CheckoutSolution {

  private Map<Character, Integer> pricesBySku = ImmutableMap.of(
          'A', 50,
          'B', 30,
          'C', 20,
          'D', 15);

  public Integer checkout(String skus) {
    return pricesBySku.get(skus.charAt(0));
  }
}

