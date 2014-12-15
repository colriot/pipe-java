package ru.pipepay.model;

import com.google.gson.annotations.SerializedName;
import ru.pipepay.util.Utils;

public class Card {
  public static final String AMERICAN_EXPRESS = "American Express";
  public static final String DISCOVER = "Discover";
  public static final String JCB = "JCB";
  public static final String DINERS_CLUB = "Diners Club";
  public static final String VISA = "Visa";
  public static final String MASTERCARD = "MasterCard";
  public static final String UNKNOWN = "Unknown";

  @SerializedName("number")
  private String number;
  private transient String normalizedNumber;
  @SerializedName("cvc")
  private String cvc;
  @SerializedName("exp_month")
  private int expMonth;
  @SerializedName("exp_year")
  private int expYear;
  @SerializedName("holder")
  private String holder;

  @SerializedName("token")
  private String token;

  @SerializedName("id")
  private String id;
  @SerializedName("brand")
  private String brand;
  @SerializedName("last4")
  private String last4;

  private Card(String number, String cvc, int expMonth, int expYear, String holder) {
    this.number = number;
    this.cvc = cvc;
    this.expMonth = expMonth;
    this.expYear = expYear;
    this.holder = holder;
  }

  private Card(String token) {
    this.token = token;
  }

  public static Card fromCardInfo(String number, String cvc, int expMonth, int expYear,
      String holder) {
    return new Card(number, cvc, expMonth, expYear, holder);
  }

  public static Card fromToken(String token) {
    return new Card(token);
  }

  public String getNumber() {
    return number;
  }

  public String getNormalizedNumber() {
    if (normalizedNumber == null) {
      normalizedNumber = Utils.normalizeCardNumber(number);
    }
    return normalizedNumber;
  }

  public String getCvc() {
    return cvc;
  }

  public int getExpMonth() {
    return expMonth;
  }

  public int getExpYear() {
    return expYear;
  }

  public String getHolder() {
    return holder;
  }

  public String getToken() {
    return token;
  }

  public String getId() {
    return id;
  }

  public String getBrand() {
    if (brand == null) {
      brand = Utils.getCardBrand(getNormalizedNumber());
    }
    return brand;
  }

  public String getLast4() {
    if (last4 == null) {
      String cardDigits = getNormalizedNumber();
      if (Utils.isBlank(cardDigits)) return null;
      last4 = cardDigits.substring(cardDigits.length() - 4);
    }
    return last4;
  }

  public boolean isNumberValid() {
    final String cardDigits = getNormalizedNumber();
    return !Utils.isBlank(cardDigits) &&
        Utils.isValidLuhnNumber(cardDigits) &&
        Utils.hasValidLength(cardDigits, getBrand());
  }

  public boolean isCvcValid() {
    if (Utils.isBlank(cvc)) return false;
    cvc = cvc.trim();
    final int cvcLength = cvc.length();
    boolean isLengthValid = (brand == null && (cvcLength == 3 || cvcLength == 4)) ||
        (AMERICAN_EXPRESS.equals(brand) && cvcLength == 4) ||
        (!AMERICAN_EXPRESS.equals(brand) && cvcLength == 3);
    return Utils.isPositiveNumber(cvc) && isLengthValid;
  }

  public boolean isExpiryDateValid() {
    return 1 <= expMonth && expMonth <= 12 && expYear > 0 && Utils.hasDatePassed(expMonth, expYear);
  }

  @Override public String toString() {
    return "Card{" +
        "number='" + number + '\'' +
        ", normalizedNumber='" + getNormalizedNumber() + '\'' +
        ", cvc='" + cvc + '\'' +
        ", expMonth=" + expMonth +
        ", expYear=" + expYear +
        ", holder='" + holder + '\'' +
        ", token='" + token + '\'' +
        ", id='" + id + '\'' +
        ", brand='" + getBrand() + '\'' +
        ", last4='" + getLast4() + '\'' +
        '}';
  }
}
