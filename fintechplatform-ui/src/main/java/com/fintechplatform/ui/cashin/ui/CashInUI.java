package com.fintechplatform.ui.cashin.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.cashin.api.CashInAPIModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.cashin.CashInActivity;
import com.fintechplatform.ui.cashin.CashInContract;
import com.fintechplatform.ui.models.DataAccount;

public class CashInUI {

    private String hostName;
    private boolean sandbox;
    private DataAccount configuration;

    public CashInUI(String hostName, DataAccount configuration, boolean sandbox) {
        this.hostName = hostName;
        this.configuration = configuration;
        this.sandbox = sandbox;
    }

    public void startActivity(Context context, Long amount) {
        Intent intent = new Intent(context, CashInActivity.class);
        Bundle args = new Bundle();
        args.putString("hostname", hostName);
        args.putParcelable("dataAccount", configuration);
        args.putBoolean("isSandbox", sandbox);
        args.putLong("initialAmount", amount);
        intent.putExtras(args);
        context.startActivity(intent);
    }

    public void startActivity(Context context) {
        Intent intent = new Intent(context, CashInActivity.class);
        Bundle args = new Bundle();
        args.putString("hostname", hostName);
        args.putParcelable("dataAccount", configuration);
        args.putBoolean("isSandbox", sandbox);
        intent.putExtras(args);
        context.startActivity(intent);
    }

    public CashInFragment createFragment(Long amount) {
        return CashInFragment.Companion.newInstance(hostName, configuration, sandbox, amount);
    }

    public static class Builder {
        public static CashInViewComponent buildPayInViewComponent(Context context, CashInContract.View v, DataAccount dataAccount, String hostName, Boolean isSandbox)  {
            return DaggerCashInViewComponent.builder()
                    .cashInPresenterModule(new CashInPresenterModule(v, dataAccount))
                    .cashInAPIModule(new CashInAPIModule(hostName))
                    .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                    .balanceAPIModule(new BalanceAPIModule(hostName))
                    //.secure3DUIHelperModule(new Secure3DUIHelperModule(secure3DUI))
                    //.paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
                    .paymentCardAPIModule(new PaymentCardAPIModule(hostName, isSandbox))
                    .build();
        }
    }
}
