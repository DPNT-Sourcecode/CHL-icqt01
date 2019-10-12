package befaster.solutions.CHK;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class MultiItemPackage {

  private final ImmutableMap<Product, Integer> itemsByQuantity;
  private final int discountedPrice;

  public MultiItemPackage(ImmutableMap<Product, Integer> itemsByQuantity, int discountedPrice) {
    this.itemsByQuantity = itemsByQuantity;
    this.discountedPrice = discountedPrice;
  }

  public ImmutableMap<Product, Integer> getItemsByQuantity() {
    return itemsByQuantity;
  }

  public int getDiscountedPrice() {
    return discountedPrice;
  }

  public boolean basketContainsAllItems(Map<Product, Integer> basket) {
    return itemsByQuantity.entrySet().stream()
        .allMatch(item -> basket.getOrDefault(item.getKey(), 0) >= item.getValue());
  }

  public int getDiscount() {
    int undiscountedPrice =
        itemsByQuantity.entrySet().stream()
            .mapToInt(entry -> entry.getKey().getUnitPrice() * entry.getValue())
            .sum();
    return undiscountedPrice - discountedPrice;
  }
}


