package ru.pipepay.model;

import com.google.gson.annotations.SerializedName;

public class Error {
  @SerializedName("type")
  private String type;
  @SerializedName("code")
  private String code;
  @SerializedName("message")
  private String message;
  //private Map extras;

  public String getType() {
    return type;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
