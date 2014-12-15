package ru.pipepay.model;

import com.google.gson.annotations.SerializedName;

public class Token {
  @SerializedName("id")
  private String id;
  @SerializedName("card")
  private Card card;

  public String getId() {
    return id;
  }

  public Card getCard() {
    return card;
  }
}
