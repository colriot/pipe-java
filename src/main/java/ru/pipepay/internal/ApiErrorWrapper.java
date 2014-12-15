package ru.pipepay.internal;

import ru.pipepay.model.Error;

public final class ApiErrorWrapper {
  public final Error error;

  public ApiErrorWrapper(Error error) {
    this.error = error;
  }
}
