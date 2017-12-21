package com.dwaplatform.android.card.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.card.api.PaymentCardRestApiModule;

/**
 * Created by ingrid on 13/12/17.
 */

public class PaymentCardUI {

    String hostname;
    boolean sandbox;

    protected static PaymentCardUI instance;

    public PaymentCardUI(String hostname, boolean sandbox) {
        this.hostname = hostname;
        this.sandbox = sandbox;
    }

    protected PaymentCardViewComponent buildPaymentCardComponent(Context context, PaymentCardContract.View view) {
        return DaggerPaymentCardViewComponent.builder()
                .paymentCardPresenterModule(new PaymentCardPresenterModule(view))
                .paymentCardRestApiModule(new PaymentCardRestApiModule(instance.hostname, instance.sandbox))
                .keyChainModule(new KeyChainModule(context))
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .build();
    }

    public static PaymentCardViewComponent createPaymentCardComponent(Context context, PaymentCardContract.View view) {
        return instance.buildPaymentCardComponent(context, view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, PaymentCardActivity.class);
        context.startActivity(intent);
    }

}
