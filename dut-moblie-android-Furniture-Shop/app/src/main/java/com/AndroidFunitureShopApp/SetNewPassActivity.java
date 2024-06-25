package com.AndroidFunitureShopApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.AndroidFunitureShopApp.databinding.ActivitySetNewPassBinding;
import com.AndroidFunitureShopApp.databinding.LoginBinding;
import com.AndroidFunitureShopApp.model.Account.Account;
import com.AndroidFunitureShopApp.model.Account.UserInfo;
import com.AndroidFunitureShopApp.viewmodel.AccountAPIService;
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

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SetNewPassActivity extends AppCompatActivity {
    private ActivitySetNewPassBinding binding;
    AccountAPIService accountAPIService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String AccountID ;

    PaymentButtonContainer paymentButtonContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_pass);
        binding = ActivitySetNewPassBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        paymentButtonContainer = findViewById(R.id.payment_container);

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

        //AccountID = getIntent().getStringExtra("accID");
        binding.setNewPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //UpdatePass();
            }
        });
    }

    public void UpdatePass(){
        String pass = binding.etNewPassword.getText().toString().trim();
        String confirmPass = binding.etConfirmPassword.getText().toString().trim();
        accountAPIService = new AccountAPIService();
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Please, enter your password!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(confirmPass)) {
            Toast.makeText(getApplicationContext(), "Please, enter your confirm password!", Toast.LENGTH_SHORT).show();
        } else {
            if(pass.equals(confirmPass)){
                compositeDisposable.add(accountAPIService.UpdatePassword(Integer.parseInt(AccountID), pass)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                accountModel -> {
                                    if (accountModel.isSuccess()){
                                        Log.d("DEBUG", "success repass");
                                        Toast.makeText(getApplicationContext(), "Update Password Success!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SetNewPassActivity.this, SuccessRepassActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Log.d("DEBUG", "fail");
                                        Toast.makeText(getApplicationContext(), accountModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Log.d("DEBUG", "fail");
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
            }   else Toast.makeText(getApplicationContext(), "Password and confirm password not equals!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}