package befaster.solutions.CHK;

import com.google.common.collect.ImmutableMap;

public class MultiItemPackage {

    private final ImmutableMap<Product, Integer> itemsByQuantity;

    public MultiItemPackage(ImmutableMap<Product, Integer> itemsByQuantity) {
        this.itemsByQuantity = itemsByQuantity;
    }

    public void getDiscount() {

    }
}

