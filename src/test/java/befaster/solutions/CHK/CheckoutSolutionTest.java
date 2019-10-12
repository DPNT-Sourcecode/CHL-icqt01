package befaster.solutions.CHK;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckoutSolutionTest {

    private CheckoutSolution checkout = new CheckoutSolution();

    @Test
    public void testSingleItemReturnsItsPrice() {
        assertEquals(50, checkout.checkout("A").intValue());
    }

    @Test
    public void testMultipleItemsReturnsCombinedPrice() {
        assertEquals(65, checkout.checkout("BCD").intValue());
    }

    @Test
    public void testInvalidItemInBasketReturnsNegativeOne() {
        assertEquals(-1, checkout.checkout("ABCDEFG").intValue());
    }

    @Test
    public void testMultibuyItems() {
        assertEquals(130, checkout.checkout("AAA").intValue());
    }

}




