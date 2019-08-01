package com.fintechplatform.ui.payout.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.cashout.api.CashOutAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.iban.ui.IbanUI;
import com.fintechplatform.ui.models.DataAccount;

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
                .cashOutAPIModule(new CashOutAPIModule(instance.hostname))
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), hostname)))
                .balanceAPIModule(new BalanceAPIModule(instance.hostname))
                .ibanAPIModule(new IbanAPIModule(instance.hostname))
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
