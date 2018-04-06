package com.fintechplatform.ui.cashin.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.cashin.api.CashInAPIModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.card.ui.PaymentCardUI;
import com.fintechplatform.ui.cashin.CashInActivity;
import com.fintechplatform.ui.cashin.CashInContract;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.secure3d.ui.Secure3DUI;

public class CashInUI {

    private String hostName;
    private boolean sandbox;
    private DataAccount configuration;
    private Secure3DUI secure3DUI;
    private PaymentCardUI paymentCardUI;


    protected static CashInUI instance;

    protected CashInUI() {
    }

    public CashInUI(String hostName, DataAccount configuration, Secure3DUI secure3DUI, PaymentCardUI paymentCardUI, boolean sandbox) {
        this.hostName = hostName;
        this.configuration = configuration;
        this.secure3DUI = secure3DUI;
        this.paymentCardUI = paymentCardUI;
        this.sandbox = sandbox;
    }

    protected CashInViewComponent buildPayInViewComponent(Context context, CashInContract.View v)  {
        return DaggerCashInViewComponent.builder()
                .cashInPresenterModule(new CashInPresenterModule(v, instance.configuration))
                .cashInAPIModule(new CashInAPIModule(instance.hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .balanceAPIModule(new BalanceAPIModule(instance.hostName))
                .secure3DUIHelperModule(new Secure3DUIHelperModule(secure3DUI))
                .paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
                .paymentCardAPIModule(new PaymentCardAPIModule(instance.hostName, sandbox))
                .build();
    }

    public static CashInViewComponent createPayInViewComponent(Context context, CashInContract.View v)  {
        return instance.buildPayInViewComponent(context, v);
    }

    public void start(Context context, Long amount) {
        instance = this;
        Intent intent = new Intent(context, CashInActivity.class);
        intent.putExtra("initialAmount", amount);
        context.startActivity(intent);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, CashInActivity.class);
        context.startActivity(intent);
    }
}
