package befaster.solutions.CHK;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiItemPackage {

  private final ImmutableMap<Product, Integer> itemsByQuantity;
  private final int discountedPrice;

  public MultiItemPackage(ImmutableMap<Product, Integer> itemsByQuantity, int discountedPrice) {
    this.itemsByQuantity = itemsByQuantity;
    this.discountedPrice = discountedPrice;
  }

  public static MultiItemPackage itemDiscount(Product product, int quantity, int discountedPrice) {
    return new MultiItemPackage(ImmutableMap.of(product, quantity), discountedPrice);
  }

  public static MultiItemPackage freeItem(Map<Product, Integer> itemsToBuy, Map<Product, Integer> freeItems) {
    Map<Product, Integer> combinedPackage = Stream.concat(itemsToBuy.entrySet().stream(), freeItems.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
    int combinedPrice = itemsToBuy.entrySet().stream().mapToInt(entry -> entry.getKey().getUnitPrice() * entry.getValue()).sum();
    return new MultiItemPackage(ImmutableMap.copyOf(combinedPackage), combinedPrice);
  }

  public ImmutableMap<Product, Integer> getItemsByQuantity() {
    return itemsByQuantity;
  }

  public int getDiscountedPrice() {
    return discountedPrice;
  }

  public PackageEvaluation evaluatePackage(Map<Product, Integer> basket) {
    Map<Product, Integer> copy = new HashMap<>(basket);
    int numBaskets = 0;
    while (basketContainsAllItems(copy)) {
      numBaskets++;
      removePackageItemsFromBasket(copy);
    }
    return new PackageEvaluation(numBaskets, numBaskets * getDiscount());
  }

  public boolean basketContainsAllItems(Map<Product, Integer> basket) {
    return itemsByQuantity.entrySet().stream()
        .allMatch(item -> basket.getOrDefault(item.getKey(), 0) >= item.getValue());
  }

  public void removePackageItemsFromBasket(Map<Product, Integer> basket) {
    itemsByQuantity
            .forEach((product, quantity) -> basket.merge(product, -quantity, Integer::sum));
  }

  public int getDiscount() {
    int undiscountedPrice =
        itemsByQuantity.entrySet().stream()
            .mapToInt(entry -> entry.getKey().getUnitPrice() * entry.getValue())
            .sum();
    return undiscountedPrice - discountedPrice;
  }

  public static class PackageEvaluation {
    private final int numPossiblePackages;
    private final int totalDiscount;

    private PackageEvaluation(int numPossiblePackages, int totalDiscount) {
      this.numPossiblePackages = numPossiblePackages;
      this.totalDiscount = totalDiscount;
    }

    public int getNumPossiblePackages() {
      return numPossiblePackages;
    }

    public int getTotalDiscount() {
      return totalDiscount;
    }
  }
}



