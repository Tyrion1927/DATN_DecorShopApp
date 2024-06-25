package com.AndroidFunitureShopApp;

import android.app.Application;
import android.os.Build;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PayPalCheckout.setConfig(new CheckoutConfig(
                    this,
                    "ATN8P7UofvgSDUo1d7Qn-YGFHMTy65o5_wWCM_SiR3h8brh4x0YT8Iw4Fyyg5m8lmlSeuAOrs_pvVvZd",
                    Environment.SANDBOX,
                    CurrencyCode.USD,
                    UserAction.PAY_NOW,
                    "com.AndroidFunitureShopApp://paypalpay"
            ));
        }
    }
}
