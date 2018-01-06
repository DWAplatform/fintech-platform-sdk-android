package com.dwaplatform.android.payout.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.iban.ui.IbanUI;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.payout.api.PayOutAPIModule;

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
                .keyChainModule(new KeyChainModule(context))
                .netModule(new NetModule(Volley.newRequestQueue(context)))
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
