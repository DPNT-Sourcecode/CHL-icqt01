package befaster.solutions.CHK;

import com.google.common.collect.ImmutableMap;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Product {

  private final char sku;
  private final int unitPrice;
  private final SortedMap<Integer, Integer> multiBuyPrices =
      new TreeMap<>(Comparator.reverseOrder());

  public Product(char sku, int unitPrice) {
    this.sku = sku;
    this.unitPrice = unitPrice;
  }

  public Product withMultibuyOffer(int units, int price) {
    multiBuyPrices.put(units, price);
    return this;
  }

  public int getTotalPrice(int quantity) {
    int remaining = quantity;
    int total = 0;
    for (Map.Entry<Integer, Integer> multibuyEntry : multiBuyPrices.entrySet()) {
      while (remaining >= multibuyEntry.getKey()) {
        total += multibuyEntry.getValue();
        remaining -= multibuyEntry.getKey();
      }
    }
    return total + remaining * unitPrice;
  }
}


