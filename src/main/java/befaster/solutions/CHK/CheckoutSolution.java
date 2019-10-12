package befaster.solutions.CHK;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

  private Map<Character, Product> productsBySku = ImmutableMap.of(
          'A', new Product('A', 50).withMultibuyOffer(3, 130).withMultibuyOffer(5, 200),
          'B', new Product('B', 30).withMultibuyOffer(2, 45),
          'C', new Product('C', 20),
          'D', new Product('D', 15),
          'E', new Product('E', 40));

  public Integer checkout(String skus) {
    Map<Product, Integer> basketItems = new HashMap<>();
    for (int i = 0; i < skus.length(); i++) {
      char item = skus.charAt(i);
      if (!productsBySku.containsKey(item)) {
        return -1;
      }
      basketItems.merge(productsBySku.get(item), 1, Integer::sum);
    }
    return basketItems.entrySet().stream().mapToInt(entry -> entry.getKey().getTotalPrice(entry.getValue())).sum();
  }
}

