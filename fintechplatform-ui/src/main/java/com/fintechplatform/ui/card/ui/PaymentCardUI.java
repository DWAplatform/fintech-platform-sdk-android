package com.fintechplatform.ui.card.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.models.DataAccount;

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
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), hostname)))
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
