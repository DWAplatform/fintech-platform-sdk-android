package com.fintechplatform.android.payin.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.card.api.PaymentCardAPIModule;
import com.fintechplatform.android.card.ui.PaymentCardUI;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.payin.PayInActivity;
import com.fintechplatform.android.payin.PayInContract;
import com.fintechplatform.android.payin.api.PayInAPIModule;
import com.fintechplatform.android.secure3d.ui.Secure3DUI;

public class PayInUI {

    private String hostName;
    private boolean sandbox;
    private DataAccount configuration;
    private Secure3DUI secure3DUI;
    private PaymentCardUI paymentCardUI;


    protected static PayInUI instance;

    protected PayInUI() {
    }

    public PayInUI(String hostName, DataAccount configuration, Secure3DUI secure3DUI, PaymentCardUI paymentCardUI, boolean sandbox) {
        this.hostName = hostName;
        this.configuration = configuration;
        this.secure3DUI = secure3DUI;
        this.paymentCardUI = paymentCardUI;
        this.sandbox = sandbox;
    }

    protected PayInViewComponent buildPayInViewComponent(Context context, PayInContract.View v)  {
        return DaggerPayInViewComponent.builder()
                .payInPresenterModule(new PayInPresenterModule(v, instance.configuration))
                .payInAPIModule(new PayInAPIModule(instance.hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .balanceAPIModule(new BalanceAPIModule(instance.hostName))
                .secure3DUIHelperModule(new Secure3DUIHelperModule(secure3DUI))
                .paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
                .paymentCardAPIModule(new PaymentCardAPIModule(instance.hostName, sandbox))
                .build();
    }

    public static PayInViewComponent createPayInViewComponent(Context context, PayInContract.View v)  {
        return instance.buildPayInViewComponent(context, v);
    }

    public void start(Context context, Long amount) {
        instance = this;
        Intent intent = new Intent(context, PayInActivity.class);
        intent.putExtra("initialAmount", amount);
        context.startActivity(intent);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, PayInActivity.class);
        context.startActivity(intent);
    }
}
