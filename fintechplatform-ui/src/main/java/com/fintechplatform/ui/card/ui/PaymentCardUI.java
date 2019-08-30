package com.fintechplatform.ui.card.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.models.DataAccount;

public class PaymentCardUI {

    private String hostname;
    private boolean sandbox;
    private DataAccount dataAccount;

    public PaymentCardUI(String hostname, DataAccount dataAccount, boolean sandbox) {
            this.hostname = hostname;
            this.sandbox = sandbox;
            this.dataAccount = dataAccount;
    }

    public void start(Context context) {
        Intent intent = new Intent(context, PaymentCardActivity.class);
        Bundle args = new Bundle();
        args.putString("hostname", hostname);
        args.putParcelable("dataAccount", dataAccount);
        args.putBoolean("isSandbox", sandbox);
        context.startActivity(intent);
    }
    /*
    public PaymentCardFragment createFragment(String hostName, DataAccount dataAccount, Boolean isSandbox) {
        return PaymentCardFragment.newInstance(hostName, dataAccount, isSandbox);
    }*/

    public static class Builder {
        public static PaymentCardViewComponent buildPaymentCardComponent(Context context, PaymentCardContract.View view, String hostname, DataAccount dataAccount, boolean sandbox) {
            return DaggerPaymentCardViewComponent.builder()
                    .paymentCardPresenterModule(new PaymentCardPresenterModule(view, dataAccount))
                    .paymentCardAPIModule(new PaymentCardAPIModule(hostname, sandbox))
                    .netModule(new NetModule(Volley.newRequestQueue(context), hostname))
                    .build();
        }
    }

}
