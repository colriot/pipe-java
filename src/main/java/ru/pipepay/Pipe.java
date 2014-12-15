package ru.pipepay;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import ru.pipepay.internal.AmountWrapper;
import ru.pipepay.internal.ApiErrorWrapper;
import ru.pipepay.internal.CardWrapper;
import ru.pipepay.model.Card;
import ru.pipepay.model.Payment;
import ru.pipepay.model.Token;

public class Pipe {

  private static final String HOST = "http://api.pipepay.ru/";
  private static final String VERSION = "1.0.0-SNAPSHOT";
  private static final MediaType CONTENT_TYPE = MediaType.parse("application/json");

  private final OkHttpClient client;
  private final Gson gson;
  private final String publicKey;

  public Pipe(String publicKey) {
    if (publicKey == null) throw new IllegalArgumentException("publicKey == null");
    this.publicKey = publicKey;
    this.client = new OkHttpClient();
    this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
  }

  private Headers getHeaders() {
    return Headers.of(
        "Authorization", Credentials.basic(publicKey, ""),
        "User-Agent", String.format(Locale.US, "Pipe Java bindings v%s", VERSION)
        );
  }

  private <RESPONSE> RESPONSE newRequest(String path, Object body, Class<RESPONSE> cls) {
    try {
      Call call = newCall(path, body);
      Response response = call.execute();

      final int code = response.code();
      if (response.isSuccessful()) {
        return gson.fromJson(response.body().charStream(), cls);
      } else if (code >= 400 && code < 500) {
        ApiErrorWrapper apiError = gson.fromJson(response.body().charStream(), ApiErrorWrapper.class);
        throw new ApiException(apiError.error);
      } else {
        throw new ApiException(response.message());
      }
    } catch (IOException e) {
      throw new PipeException("Failed to execute request due to network problems or timeout.", e);
    }
  }

  private Call newCall(String path, Object body) {
    URI uri = URI.create(HOST).resolve(path);

    String content = gson.toJson(body);
    Request request = new Request.Builder()
        .url(uri.toString())
        .headers(getHeaders())
        .post(RequestBody.create(CONTENT_TYPE, content))
        .build();

    return client.newCall(request);
  }


  public Token token(Card card) throws IOException {
    return newRequest("tokens", new CardWrapper(card), Token.class);
  }

  public Payment payment(PaymentRequest payment) throws IOException {
    return newRequest("payments", payment, Payment.class);
  }

  public Payment capture(String paymentId, int amount) throws IOException {
    return newRequest(
        String.format(Locale.US, "payments/%s/capture", paymentId),
        new AmountWrapper(amount),
        Payment.class);
  }

  public Payment refund(String paymentId, int amount) throws IOException {
    return newRequest(
        String.format(Locale.US, "payments/%s/refunds", paymentId),
        new AmountWrapper(amount),
        Payment.class);
  }
}
