package befaster.solutions.CHK;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckoutSolution {

  private Map<Character, Product> productsBySku;

  private List<MultiItemPackage> multiItemPackages = new ArrayList<>();

  public CheckoutSolution() {
    productsBySku = readPriceList();
    multiItemPackages.addAll(readMultibuyList());
    multiItemPackages.addAll(readFreeItemsList());
    multiItemPackages.sort(Comparator.comparing(MultiItemPackage::getDiscount).reversed());
  }

  private ImmutableMap<Character, Product> readPriceList() {
    ImmutableMap.Builder<Character, Product> builder = ImmutableMap.builder();
    readFile(
        "prices.txt",
        line -> {
          String[] pieces = line.split(",");
          char sku = pieces[0].charAt(0);
          int price = Integer.parseInt(pieces[1]);
          builder.put(sku, new Product(sku, price));
        });
    return builder.build();
  }

  private List<MultiItemPackage> readMultibuyList() {
    List<MultiItemPackage> packages = new ArrayList<>();
    readFile(
        "multibuys.txt",
        line -> {
          String[] pieces = line.split(",");
          char sku = pieces[0].charAt(0);
          int qty = Integer.parseInt(pieces[1]);
          int price = Integer.parseInt(pieces[2]);
          packages.add(MultiItemPackage.itemDiscount(productsBySku.get(sku), qty, price));
        });
    return packages;
  }

  private List<MultiItemPackage> readFreeItemsList() {
    List<MultiItemPackage> packages = new ArrayList<>();
    readFile(
        "freeitems.txt",
        line -> {
          String[] pieces = line.split(",");
          Map<Product, Integer> itemsToBuy = parseString(pieces[0]);
          Map<Product, Integer> freeItems = parseString(pieces[1]);
          packages.add(MultiItemPackage.freeItem(itemsToBuy, freeItems));
        });
    return packages;
  }

  private Map<Product, Integer> parseString(String str) {
    return str.chars()
        .mapToObj(sku -> productsBySku.get((char) sku))
        .collect(Collectors.toMap(Function.identity(), p -> 1, Integer::sum));
  }

  private void readFile(String filename, Consumer<String> lineAction) {
    try (Scanner scanner = new Scanner(getClass().getResourceAsStream(filename))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.isEmpty()) {
          continue;
        }
        lineAction.accept(line);
      }
    }
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
