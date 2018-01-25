package com.fintechplatform.android.payout.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.iban.ui.IbanUI;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.payout.api.PayOutAPIModule;

public class PayOutUI {

    private String hostname;
    private IbanUI ibanUI;
    private DataAccount configuration;

    protected static PayOutUI instance;

    protected PayOutUI() {
    }

    public PayOutUI(String hostname, DataAccount configuration, IbanUI ibanUI) {
        this.ibanUI = ibanUI;
        this.configuration = configuration;
        this.hostname = hostname;
    }

    protected PayOutViewComponent buildPayOutViewComponent(Context context, PayOutContract.View view) {
        return DaggerPayOutViewComponent.builder()
                .payOutPresenterModule(new PayOutPresenterModule(view, instance.configuration))
                .payOutAPIModule(new PayOutAPIModule(instance.hostname))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostname))
                .balanceAPIModule(new BalanceAPIModule(instance.hostname))
                .ibanUIModule(new IbanUIModule(ibanUI))
                .build();
    }

    public static PayOutViewComponent createPayOutViewComponent(Context context, PayOutContract.View view) {
        return instance.buildPayOutViewComponent(context, view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, PayOutActivity.class);
        context.startActivity(intent);
    }

}
