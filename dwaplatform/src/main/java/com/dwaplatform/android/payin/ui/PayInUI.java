package com.dwaplatform.android.payin.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.payin.PayInActivity;
import com.dwaplatform.android.payin.PayInContract;
import com.dwaplatform.android.payin.api.PayInAPIModule;
import com.dwaplatform.android.secure3d.ui.Secure3DUI;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class PayInUI {

    String hostName;
    DataAccount configuration;
    Secure3DUI secure3DUI;
    PaymentCardUI paymentCardUI;


    protected static PayInUI instance;

    protected PayInUI() {
    }

    public PayInUI(String hostName, DataAccount configuration, Secure3DUI secure3DUI, PaymentCardUI paymentCardUI) {
        this.hostName = hostName;
        this.configuration = configuration;
        this.secure3DUI = secure3DUI;
        this.paymentCardUI = paymentCardUI;
    }

    protected PayInViewComponent buildPayInViewComponent(Context context, PayInContract.View v)  {
        return DaggerPayInViewComponent.builder()
                .payInPresenterModule(new PayInPresenterModule(v, instance.configuration))
                .payInAPIModule(new PayInAPIModule(instance.hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .balanceAPIModule(new BalanceAPIModule(instance.hostName))
                .secure3DUIModule(new Secure3DUIModule(secure3DUI))
                .paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
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

    public String getHostName() {
        return hostName;
    }

//    public String getToken() {
//        return keyChain.get("tokenuser");
//    }

    public DataAccount getConfiguration() {
        return configuration;
    }
}
