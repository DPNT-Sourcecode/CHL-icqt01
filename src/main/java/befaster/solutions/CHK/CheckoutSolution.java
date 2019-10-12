package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CheckoutSolution {

  private Map<Character, Integer> pricesBySku = ImmutableMap.of(
          'A', 50,
          'B', 30,
          'C', 20,
          'D', 15);

  public Integer checkout(String skus) {
    int sum = 0;
    for (int i = 0; i < skus.length(); i++) {
      char item = skus.charAt(i);
      if (!pricesBySku.containsKey(item)) {
        return -1;
      }
      sum += pricesBySku.get(item);
    }
    return sum;
  }
}



