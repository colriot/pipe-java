package ru.pipepay.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Payment {
  @SerializedName("id")
  private String id;
  @SerializedName("amount")
  private int amount;
  @SerializedName("paid_amount")
  private Integer paidAmount;
  @SerializedName("refunded_amount")
  private Integer refundedAmount;
  @SerializedName("currency")
  private String currency;
  @SerializedName("description")
  private String description;
  @SerializedName("status")
  private String status;

  @SerializedName("redirect_url")
  private String redirectUrl;

  @SerializedName("failure_reason")
  private String failureReason;
  @SerializedName("failure_code")
  private String failureCode;
  @SerializedName("rrn")
  private String rrn;
  @SerializedName("auth_code")
  private String authCode;
  @SerializedName("created_at")
  private Date createdAt;

  @SerializedName("card")
  private Card card;

  public String getId() {
    return id;
  }

  public int getAmount() {
    return amount;
  }

  public Integer getPaidAmount() {
    return paidAmount;
  }

  public Integer getRefundedAmount() {
    return refundedAmount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getDescription() {
    return description;
  }

  public String getStatus() {
    return status;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public String getFailureReason() {
    return failureReason;
  }

  public String getFailureCode() {
    return failureCode;
  }

  public String getRrn() {
    return rrn;
  }

  public String getAuthCode() {
    return authCode;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Card getCard() {
    return card;
  }

  @Override public String toString() {
    return "Payment{" +
        "id='" + id + '\'' +
        ", amount=" + amount +
        ", paidAmount=" + paidAmount +
        ", refundedAmount=" + refundedAmount +
        ", currency='" + currency + '\'' +
        ", description='" + description + '\'' +
        ", status='" + status + '\'' +
        ", redirectUrl='" + redirectUrl + '\'' +
        ", failureReason='" + failureReason + '\'' +
        ", failureCode='" + failureCode + '\'' +
        ", rrn='" + rrn + '\'' +
        ", authCode='" + authCode + '\'' +
        ", createdAt=" + createdAt +
        ", card=" + card +
        '}';
  }
}
