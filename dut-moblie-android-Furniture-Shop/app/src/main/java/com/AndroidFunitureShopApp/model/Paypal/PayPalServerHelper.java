package com.AndroidFunitureShopApp.model.Paypal;

import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PayPalServerHelper {
    private static final String CLIENT_ID = "ATN8P7UofvgSDUo1d7Qn-YGFHMTy65o5_wWCM_SiR3h8brh4x0YT8Iw4Fyyg5m8lmlSeuAOrs_pvVvZd";
    private static final String CLIENT_SECRET = "EGRvbae_mPWAuVVsJB9H3P97aIrx0kICjkeOXA6PflBCmFzSZ5fJXnlJ4pEvfb72ghbFQwQuhUqWZBzf";
    private static final String SANDBOX_URL = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
    private static final String CREATE_ORDER_URL = "https://api-m.sandbox.paypal.com/v2/checkout/orders";
    public static void getAccessToken(Callback callback) {
        OkHttpClient client = new OkHttpClient();

        String credential = Credentials.basic(CLIENT_ID, CLIENT_SECRET);

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url(SANDBOX_URL)
                .post(body)
                .header("Authorization", credential)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        client.newCall(request).enqueue(callback);
    }
    public static void createOrder(String accessToken, String amountValue, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        String json = "{"
                + "\"intent\":\"CAPTURE\","
                + "\"purchase_units\":[{"
                + "\"amount\":{"
                + "\"currency_code\":\"USD\","
                + "\"value\":\"" + amountValue + "\""
                + "}"
                + "}]"
                + "}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
                .url(CREATE_ORDER_URL)
                .post(body)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(callback);
    }
}
