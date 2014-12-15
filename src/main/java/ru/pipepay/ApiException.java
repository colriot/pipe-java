package ru.pipepay;

import ru.pipepay.model.Error;

public class ApiException extends PipeException {
  private Error error;

  public ApiException(String message) {
    super(message);
  }

  public ApiException(Error error) {
    super(error.getMessage());
    this.error = error;
  }

  public Error getError() {
    return error;
  }
}
