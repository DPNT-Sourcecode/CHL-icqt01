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
    assertEquals(-1, checkout.checkout("ABCDEFG-").intValue());
  }

  @Test
  public void testMultibuyItems() {
    assertEquals(130, checkout.checkout("AAA").intValue());
  }

  @Test
  public void testMultibuyWithAdditionalItems() {
    // 4A = 180, 2B = 45, C = 20, D = 15
    assertEquals(260, checkout.checkout("ABACABAD").intValue());
  }

  @Test
  public void testEmptyBasketReturnsZero() {
    assertEquals(0, checkout.checkout("").intValue());
  }

  @Test
  public void testCaseSensitive() {
    assertEquals(-1, checkout.checkout("abc").intValue());
  }

  @Test
  public void testLargerMultibuysOnSameItem() {
    assertEquals(200, checkout.checkout("AAAAA").intValue());
  }

  @Test
  public void testCombiningMultibuysOnSameItem() {
    assertEquals(380, checkout.checkout("AAAAA" + "AAA" + "A").intValue());
  }

  @Test
  public void testProductE() {
    assertEquals(80, checkout.checkout("EE").intValue());
  }

  @Test
  public void testFreeOffer() {
    assertEquals(80, checkout.checkout("EBE").intValue());
  }

  @Test
  public void testFreeOfferClashingWithMultiBuy() {
    assertEquals(160, checkout.checkout("EEBBEE").intValue());
  }

  @Test
  public void testBestMultibuyDiscountApplies() {
    // 14A = 4(3A) + 2A = 4(130) + 100 = 620
    // 14A = 2(5A) + 3A + A = 2(200) + 130 + 50 = 580
    assertEquals(580, checkout.checkout("AAAAA" + "AAAAA" + "AAAA").intValue());
  }

  @Test
  public void testNewF() {
    assertEquals(20, checkout.checkout("FF").intValue());
  }

  @Test
  public void testBuyTwoFGetOneFree() {
    assertEquals(20, checkout.checkout("FFF").intValue());
  }

  @Test
  public void testOneEachOfGIJLM() {
    assertEquals(20 + 35 + 60 + 90 + 15, checkout.checkout("GIJLM").intValue());
  }
}






