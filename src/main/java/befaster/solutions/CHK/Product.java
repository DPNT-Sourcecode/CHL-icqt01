package befaster.solutions.CHK;

import java.util.Objects;

public class Product {

  private final char sku;
  private final int unitPrice;

  public Product(char sku, int unitPrice) {
    this.sku = sku;
    this.unitPrice = unitPrice;
  }

  public int getUnitPrice() {
    return unitPrice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return sku == product.sku;
  }

  @Override
  public int hashCode() {
    return Objects.hash(sku);
  }
}


