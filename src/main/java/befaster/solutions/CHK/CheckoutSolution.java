package befaster.solutions.CHK;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class CheckoutSolution {

  private Map<Character, Product> productsBySku =
      ImmutableMap.of(
          'A', new Product('A', 50),
          'B', new Product('B', 30),
          'C', new Product('C', 20),
          'D', new Product('D', 15),
          'E', new Product('E', 40));

  private SortedSet<MultiItemPackage> multiItemPackages =
      new TreeSet<>(Comparator.comparing(MultiItemPackage::getDiscount).reversed());

  public CheckoutSolution() {
    // multi-item discounts
    multiItemPackages.add(MultiItemPackage.itemDiscount(productsBySku.get('A'), 3, 130));
    multiItemPackages.add(MultiItemPackage.itemDiscount(productsBySku.get('A'), 5, 200));
    multiItemPackages.add(MultiItemPackage.itemDiscount(productsBySku.get('B'), 2, 45));

    // buy 2E get B free
    multiItemPackages.add(
        MultiItemPackage.freeItem(
            ImmutableMap.of(productsBySku.get('E'), 2),
            ImmutableMap.of(productsBySku.get('B'), 1)));
  }

  public Integer checkout(String skus) {
    Map<Product, Integer> basketItems = new HashMap<>();
    for (int i = 0; i < skus.length(); i++) {
      char item = skus.charAt(i);
      if (!productsBySku.containsKey(item)) {
        return -1;
      }
      basketItems.merge(productsBySku.get(item), 1, Integer::sum);
    }
    List<MultiItemPackage> packagesInBasket = new ArrayList<>();
    for (MultiItemPackage packageToCheck : multiItemPackages) {
      while (packageToCheck.basketContainsAllItems(basketItems)) {
        packagesInBasket.add(packageToCheck);
        packageToCheck.removePackageItemsFromBasket(basketItems);
      }
    }
    return packagesInBasket.stream().mapToInt(MultiItemPackage::getDiscountedPrice).sum()
        + basketItems.entrySet().stream()
            .mapToInt(entry -> entry.getKey().getUnitPrice() * entry.getValue())
            .sum();
  }
}
