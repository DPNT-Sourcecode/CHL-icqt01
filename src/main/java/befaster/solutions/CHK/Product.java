package befaster.solutions.CHK;

import com.google.common.collect.ImmutableMap;

import java.util.SortedMap;
import java.util.TreeMap;

public class Product {

    private final char sku;
    private final int unitPrice;
    private final SortedMap<Integer, Integer> multiBuyPrices = new TreeMap<>();

    public Product(char sku, int unitPrice) {
        this.sku = sku;
        this.unitPrice = unitPrice;
    }

    public Product withMultibuyOffer(int units, int price) {
        multiBuyPrices.put(units, price);
        return this;
    }

    public int getTotalPrice(int quantity) {
        return quantity * unitPrice; //Will come back to this in a minute
    }
}

