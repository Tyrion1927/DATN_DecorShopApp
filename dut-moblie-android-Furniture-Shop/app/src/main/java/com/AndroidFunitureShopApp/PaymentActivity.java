package com.AndroidFunitureShopApp;

import static com.AndroidFunitureShopApp.model.Paypal.PayPalServerHelper.createOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.AndroidFunitureShopApp.databinding.ActivityPaymentBinding;
import com.AndroidFunitureShopApp.model.Account.UserInfo;
import com.AndroidFunitureShopApp.model.Cart.CartItem;
import com.AndroidFunitureShopApp.model.Paypal.PayPalServerHelper;
import com.AndroidFunitureShopApp.viewmodel.OrderAPIService;
import com.AndroidFunitureShopApp.viewmodel.Utils;
import com.google.gson.Gson;
import com.paypal.android.corepayments.CoreConfig;
import com.paypal.android.corepayments.Environment;
import com.paypal.android.corepayments.PayPalSDKError;
import com.paypal.android.paymentbuttons.PayPalButton;
import com.paypal.android.paypalnativepayments.PayPalNativeCheckoutClient;
import com.paypal.android.paypalnativepayments.PayPalNativeCheckoutListener;
import com.paypal.android.paypalnativepayments.PayPalNativeCheckoutRequest;
import com.paypal.android.paypalnativepayments.PayPalNativeCheckoutResult;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;
    private OrderAPIService orderAPIService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    PayPalServerHelper PayPalServerHelper;
    private PayPalNativeCheckoutClient payPalNativeClient;
    PaymentButtonContainer paymentButtonContainer;
    long totalPrice;
    int totalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        orderAPIService = new OrderAPIService();
        paymentButtonContainer = findViewById(R.id.payment_button_container);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            paymentButtonContainer.setup(
                    new CreateOrder() {
                        @Override
                        public void create(@NotNull CreateOrderActions createOrderActions) {
                            Log.d("TAG", "create: ");
                            ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                            purchaseUnits.add(
                                    new PurchaseUnit.Builder()
                                            .amount(
                                                    new Amount.Builder()
                                                            .currencyCode(CurrencyCode.USD)
                                                            .value("5.00")
                                                            .build()
                                            )
                                            .build()
                            );
                            OrderRequest order = new OrderRequest(
                                    OrderIntent.CAPTURE,
                                    new AppContext.Builder()
                                            .userAction(UserAction.PAY_NOW)
                                            .build(),
                                    purchaseUnits
                            );
                            createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                        }
                    },
                    new OnApprove() {
                        @Override
                        public void onApprove(@NotNull Approval approval) {
                            approval.getOrderActions().capture(new OnCaptureComplete() {
                                @Override
                                public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                    Log.d("TAG", String.format("CaptureOrderResult: %s", result));
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
            );
        }



        PayPalButton payPalButton = binding.payPalButton;
        payPalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalServerHelper.getAccessToken(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseBody = response.body().string();
                            String accessToken = extractAccessToken(responseBody);
                            Log.d("Access_Token:", accessToken);
                            // Giả sử bạn có giá trị động này từ đâu đó
                            String amountValue = "5.00"; // Giá trị động bạn muốn truyền vào
                            createOrder(accessToken, amountValue);
                        } else {
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Failed to get access token", Toast.LENGTH_SHORT).show());
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Request failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });



        totalPrice = getIntent().getLongExtra("totalPrice", 0);
        countItem();
        binding.editAddress.setText(UserInfo.userInfo.getDefaultAdress());
        binding.txtName.setText(UserInfo.userInfo.getFullName());
        binding.txtTotalPrice.setText(totalPrice + "$");
        binding.txtEmail.setText(UserInfo.userInfo.getEmail());
        binding.txtPhone.setText(UserInfo.userInfo.getPhone());
        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAddress = binding.editAddress.getText().toString().trim();
                CashOnDelivery(strAddress);
            }
        });

    }

    private void createOrder(String accessToken, String amountValue) {
        PayPalServerHelper.createOrder(accessToken, amountValue, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    String orderId = extractOrderId(responseBody);
                    Log.d("Order_ID:", orderId);
                    startPayPalCheckout(orderId);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to create order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Request failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String extractAccessToken(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String extractOrderId(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void startPayPalCheckout(String orderId) {
        String clientId = "ATN8P7UofvgSDUo1d7Qn-YGFHMTy65o5_wWCM_SiR3h8brh4x0YT8Iw4Fyyg5m8lmlSeuAOrs_pvVvZd";
        String returnUrl = "com.AndroidFunitureShopApp://paypalpay";
        CoreConfig coreConfig = new CoreConfig(
                clientId,
                Environment.SANDBOX // Hoặc Environment.LIVE cho môi trường sản xuất
        );
        // Tạo PayPalNativeCheckoutClient
        payPalNativeClient = new PayPalNativeCheckoutClient(
                this.getApplication(),
                coreConfig,
                returnUrl
        );
        // Thiết lập listener
        payPalNativeClient.setListener(new PayPalNativeCheckoutListener() {
            @Override
            public void onPayPalCheckoutStart() {
                // The PayPal paysheet is about to show up
                // Bạn có thể xử lý các sự kiện khi PayPal paysheet bắt đầu hiển thị
                Log.d("PayPal", "PayPal checkout started");
            }

            @Override
            public void onPayPalCheckoutSuccess(PayPalNativeCheckoutResult result) {
                // Order was approved and is ready to be captured/authorized
                // Bạn có thể xử lý các sự kiện khi giao dịch PayPal thành công
                Log.d("PayPal", "PayPal checkout successful: " + result.toString());
            }

            @Override
            public void onPayPalCheckoutFailure(PayPalSDKError error) {
                // Handle the error
                // Bạn có thể xử lý các sự kiện khi có lỗi xảy ra trong quá trình thanh toán
                Log.e("PayPal", "PayPal checkout failed: " + error.getMessage());
                error.printStackTrace();
            }

            @Override
            public void onPayPalCheckoutCanceled() {
                // The user canceled the flow
                // Bạn có thể xử lý các sự kiện khi người dùng hủy quá trình thanh toán
                Log.d("PayPal", "PayPal checkout canceled");
            }
        });
        PayPalNativeCheckoutRequest request = new PayPalNativeCheckoutRequest(orderId);
        payPalNativeClient.startCheckout(request);
    }

    private void CashOnDelivery(String strAddress) {
        if (TextUtils.isEmpty(strAddress)) {
            Toast.makeText(getApplicationContext(), "You forget enter address!!!", Toast.LENGTH_SHORT).show();
        } else {
            String strEmail = UserInfo.userInfo.getEmail();
            String strPhone = UserInfo.userInfo.getPhone();
            int idUser = UserInfo.userInfo.getId();
            Log.d("test", new Gson().toJson(Utils.cartItemList));

            compositeDisposable.add(orderAPIService.createOrder(strEmail, strPhone, totalPrice, idUser, strAddress, totalItem, new Gson().toJson(Utils.cartItemBuyList))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(accountModel -> {
                        Toast.makeText(getApplicationContext(), "Success add order", Toast.LENGTH_SHORT).show();
                        Utils.removeCartItemHadBuy();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }, throwable -> {
                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }));
        }
    }
    private void countItem() {
        totalItem = 0;
        for (int i = 0; i < Utils.cartItemBuyList.size(); i++) {
            totalItem += Utils.cartItemBuyList.get(i).getQuantity();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
}