package com.dwaplatform.android.card.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.api.PaymentCardAPIModule;
import com.dwaplatform.android.models.DataAccount;

/**
 * Created by ingrid on 13/12/17.
 */

public class PaymentCardUI {

    private String hostname;
    private boolean sandbox;
    private DataAccount dataAccount;

    protected static PaymentCardUI instance;

    public PaymentCardUI(String hostname, boolean sandbox, DataAccount dataAccount) {
        this.hostname = hostname;
        this.sandbox = sandbox;
        this.dataAccount = dataAccount;
    }

    protected PaymentCardViewComponent buildPaymentCardComponent(Context context, PaymentCardContract.View view) {
        return DaggerPaymentCardViewComponent.builder()
                .paymentCardPresenterModule(new PaymentCardPresenterModule(view, dataAccount))
                .paymentCardAPIModule(new PaymentCardAPIModule(instance.hostname, instance.sandbox))
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
