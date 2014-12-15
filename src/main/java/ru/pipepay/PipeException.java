package ru.pipepay;

public class PipeException extends RuntimeException {

  public PipeException(String message) {
    super(message);
  }

  public PipeException(String message, Throwable e) {
    super(message, e);
  }
}
