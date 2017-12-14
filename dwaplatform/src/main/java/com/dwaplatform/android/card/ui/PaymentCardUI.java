package com.dwaplatform.android.card.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.api.CardRestApiModule;

/**
 * Created by ingrid on 13/12/17.
 */

public class PaymentCardUI {

    String hostname;
    String token;
    boolean sandbox;

    protected static PaymentCardUI instance;

    protected PaymentCardUI(){ }

    public PaymentCardUI(String hostname, String token, boolean sandbox) {
        this.hostname = hostname;
        this.token = token;
        this.sandbox = sandbox;
    }

    protected PaymentCardViewComponent buildPaymentCardComponent(Context context, PaymentCardContract.View view) {
        return DaggerPaymentCardViewComponent.builder()
                .paymentCardPresenterModule(new PaymentCardPresenterModule(view))
                .cardRestApiModule(new CardRestApiModule(hostname, token, sandbox))
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .build();
    }

    public static PaymentCardViewComponent createPaymentCardComponent(Context context, PaymentCardContract.View view) {
        return instance.buildPaymentCardComponent(context, view);
    }

    void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, PaymentCardActivity.class);
        context.startActivity(intent);
    }

}
