package ru.pipepay.util;

import java.util.Arrays;
import java.util.Calendar;
import ru.pipepay.model.Card;

public final class Utils {

  private static Calendar NOW = Calendar.getInstance();

  public static boolean isBlank(String s) {
    return s == null || s.trim().length() == 0;
  }

  public static boolean isPositiveNumber(String s) {
    if (isBlank(s)) return false;

    for (int i = 0, len = s.length(); i < len; i++) {
      if (!Character.isDigit(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public static boolean hasYearPassed(int year) {
    return normalizeYear(year) < NOW.get(Calendar.YEAR);
  }

  public static boolean hasDatePassed(int month, int year) {
    // Card expires at the end of specified month, Calendar month starts at 0
    return hasYearPassed(year) ||
        NOW.get(Calendar.YEAR) == normalizeYear(year) && month < (NOW.get(Calendar.MONTH) + 1);
  }

  /** Expand two-digit year to four-digit if necessary. */
  public static int normalizeYear(int year) {
    if (0 <= year && year < 100) {
      int currentYear = NOW.get(Calendar.YEAR);
      year = currentYear / 100 + year;
    }
    return year;
  }

  public static boolean startsWithAny(String s, String... prefixes) {
    if (s == null || prefixes == null) return false;
    for (String prefix : prefixes) {
      if (s.startsWith(prefix)) {
        return true;
      }
    }
    return false;
  }

  /** Remove whitespaces and {@code -} symbols from card number. */
  public static String normalizeCardNumber(String number) {
    if (number == null) return null;
    return number.replaceAll("\\s+|-", "");
  }

  /** Check if non-empty cardNumber passes Luhn test. */
  public static boolean isValidLuhnNumber(String cardNumber) {
    int sum = 0;
    boolean isEven = false;

    for (int digit, i = cardNumber.length() - 1; i >= 0; i--, isEven = !isEven) {
      char ch = cardNumber.charAt(i);
      if (!Character.isDigit(ch)) {
        return false;
      }

      digit = Character.digit(ch, 10);
      if (isEven) {
        digit *= 2;
        if (digit > 9) digit -= 9;
      }
      sum += digit;
    }

    return sum % 10 == 0;
  }


  private static class CardBrandInfo {
    final String name;
    final String[] prefixes;
    final int[] lengths;

    CardBrandInfo(String name, String[] prefixes, int[] lengths) {
      this.name = name;
      this.prefixes = prefixes;
      this.lengths = lengths;
    }
  }

  // Based on http://en.wikipedia.org/wiki/Bank_card_number#Issuer_identification_number_.28IIN.29
  private static final String[] PREFIXES_AMERICAN_EXPRESS = {"34", "37"};
  private static final String[] PREFIXES_DISCOVER = {"60", "62", "64", "65"};
  private static final String[] PREFIXES_JCB = {"35"};
  private static final String[] PREFIXES_DINERS_CLUB = {"300", "301", "302", "303", "304", "305", "309", "36", "38", "37", "39"};
  private static final String[] PREFIXES_VISA = {"4"};
  private static final String[] PREFIXES_MASTERCARD = {"50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "61", "63", "66", "67", "68", "69"};

  // NOTE: Lengths in each array should be sorted for binarySearch to work.
  private static final int[] LENGTH_AMERICAN_EXPRESS = {15};
  private static final int[] LENGTH_DISCOVER = {16};
  private static final int[] LENGTH_JCB = {16};
  private static final int[] LENGTH_DINERS_CLUB = {14};
  private static final int[] LENGTH_VISA = {13, 16};
  private static final int[] LENGTH_MASTERCARD = {12, 13, 14, 15, 16, 17, 18, 19};
  private static final int[] LENGTH_ALL = {12, 13, 14, 15, 16, 17, 18, 19};

  private static final CardBrandInfo[] CARD_BRAND_INFO = new CardBrandInfo[] {
      new CardBrandInfo(Card.AMERICAN_EXPRESS, PREFIXES_AMERICAN_EXPRESS, LENGTH_AMERICAN_EXPRESS),
      new CardBrandInfo(Card.DISCOVER, PREFIXES_DISCOVER, LENGTH_DISCOVER),
      new CardBrandInfo(Card.JCB, PREFIXES_JCB, LENGTH_JCB),
      new CardBrandInfo(Card.DINERS_CLUB, PREFIXES_DINERS_CLUB, LENGTH_DINERS_CLUB),
      new CardBrandInfo(Card.VISA, PREFIXES_VISA, LENGTH_VISA),
      new CardBrandInfo(Card.MASTERCARD, PREFIXES_MASTERCARD, LENGTH_MASTERCARD),
  };

  public static String getCardBrand(String cardNumber) {
    for (CardBrandInfo info : CARD_BRAND_INFO) {
      if (Utils.startsWithAny(cardNumber, info.prefixes)) {
        return info.name;
      }
    }
    return Card.UNKNOWN;
  }

  public static boolean hasValidLength(String cardNumber, String brand) {
    int[] brandLength = LENGTH_ALL; // defaults for Unknown card.
    for (CardBrandInfo info : CARD_BRAND_INFO) {
      if (info.name.equals(brand)) {
        brandLength = info.lengths;
      }
    }
    return Arrays.binarySearch(brandLength, cardNumber.length()) >= 0;
  }

  private Utils() {
    // No instances.
  }
}
