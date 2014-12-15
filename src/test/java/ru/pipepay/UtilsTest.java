package ru.pipepay;

import org.junit.Test;
import ru.pipepay.model.Card;
import ru.pipepay.util.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

  @Test public void testIsPositiveNumber() {
    assertTrue(Utils.isPositiveNumber("1234"));
    assertTrue(Utils.isPositiveNumber("123456789012345678901234"));

    assertFalse(Utils.isPositiveNumber("1234-4124-1234-5678"));
    assertFalse(Utils.isPositiveNumber("-1"));
  }

  @Test public void testLuhn() {
    assertTrue(Utils.isValidLuhnNumber("5678"));
    assertTrue(Utils.isValidLuhnNumber("56613959932537"));

    assertFalse(Utils.isValidLuhnNumber("6789"));
    assertTrue(Utils.isValidLuhnNumber("1234567812345670"));

    assertTrue(Utils.isValidLuhnNumber("56613959932537102020"));
  }

  @Test public void testCardBrand() {
    assertEquals(Card.AMERICAN_EXPRESS, Utils.getCardBrand("3745000011113333"));
    assertEquals(Card.DISCOVER, Utils.getCardBrand("6490300311113333"));
    assertEquals(Card.JCB, Utils.getCardBrand("3590300311113333"));
    assertEquals(Card.DINERS_CLUB, Utils.getCardBrand("3090300311113333"));
    assertEquals(Card.VISA, Utils.getCardBrand("4745000011113333"));
    assertEquals(Card.MASTERCARD, Utils.getCardBrand("5423456708923499"));
    assertEquals(Card.UNKNOWN, Utils.getCardBrand("2900456708923499"));
  }

  @Test public void testNormalizeCardNumber() {
    assertEquals("5745000011113333", Utils.normalizeCardNumber("5745-0000-1111-3333"));
    assertEquals("4745000011113333", Utils.normalizeCardNumber("4745 0000 1111 3333"));
  }
}
