package befaster.solutions.CHK;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class CheckoutSolution {

  private Map<Character, Product> productsBySku =
      readPriceList();

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

    // buy 2F get F free
    multiItemPackages.add(
        MultiItemPackage.freeItem(
            ImmutableMap.of(productsBySku.get('F'), 2),
            ImmutableMap.of(productsBySku.get('F'), 1)));
  }

  private ImmutableMap<Character, Product> readPriceList() {
    ImmutableMap.Builder<Character, Product> builder = ImmutableMap.builder();
    try (Scanner scanner = new Scanner(getClass().getResourceAsStream("prices.txt"))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.isEmpty()) {
          continue;
        }
        String[] pieces = line.split(",");
        char sku = pieces[0].charAt(0);
        int price = Integer.parseInt(pieces[1]);
        builder.put(sku, new Product(sku, price));
      }
    }
    return builder.build();
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



