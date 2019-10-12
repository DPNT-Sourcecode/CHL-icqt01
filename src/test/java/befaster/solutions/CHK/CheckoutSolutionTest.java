package befaster.solutions.CHK;

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

}

