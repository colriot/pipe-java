package ru.pipepay;

import com.google.gson.annotations.SerializedName;
import ru.pipepay.model.Card;

public class PaymentRequest {
  @SerializedName("amount")
  private int amount;
  @SerializedName("currency")
  private String currency;
  @SerializedName("description")
  private String description;
  @SerializedName("capture")
  private boolean capture;
  @SerializedName("return_url")
  private String returnUrl;
  @SerializedName("card")
  private Card card;

  private PaymentRequest(Builder builder) {
    amount = builder.amount;
    currency = builder.currency;
    description = builder.description;
    capture = builder.capture;
    returnUrl = builder.returnUrl;
    card = builder.card;
  }

  @Override public String toString() {
    return "PaymentRequest{" +
        "amount=" + amount +
        ", currency='" + currency + '\'' +
        ", description='" + description + '\'' +
        ", capture=" + capture +
        ", returnUrl='" + returnUrl + '\'' +
        ", card=" + card +
        '}';
  }

  public static class Builder {
    private int amount;
    private String currency;
    private String description;
    private boolean capture;
    private String returnUrl;
    private Card card;

    public Builder amount(int amount) {
      this.amount = amount;
      return this;
    }

    public Builder currency(String currency) {
      this.currency = currency;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder capture() {
      this.capture = true;
      return this;
    }

    public Builder returnUrl(String returnUrl) {
      this.returnUrl = returnUrl;
      return this;
    }

    public Builder card(Card card) {
      this.card = card;
      return this;
    }

    public PaymentRequest build() {
      return new PaymentRequest(this);
    }
  }
}
