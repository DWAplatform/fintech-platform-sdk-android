package com.dwaplatform.android.payin.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.payin.PayInActivity;
import com.dwaplatform.android.payin.PayInContract;
import com.dwaplatform.android.payin.api.PayInAPIModule;
import com.dwaplatform.android.payin.models.PayInConfiguration;
import com.dwaplatform.android.secure3d.ui.Secure3DUI;
import com.dwaplatform.android.secure3d.ui.Secure3DUIModule;

/**
 * Created by tcappellari on 08/12/2017.
 */

public class PayInUI {

    String hostName;
    String token;
    PayInConfiguration configuration;
    Secure3DUI secure3DUI;

    protected static PayInUI instance;

    protected PayInUI() {
    }

    public PayInUI(String hostName, String token, PayInConfiguration configuration, Secure3DUI secure3DUI) {
        this.hostName = hostName;
        this.token = token;
        this.configuration = configuration;
        this.secure3DUI = secure3DUI;
    }

    protected PayInViewComponent buildPayInViewComponent(Context context, PayInContract.View v)  {
        return DaggerPayInViewComponent.builder()
                .payInPresenterModule(new PayInPresenterModule(v, instance.configuration))
                .payInAPIModule(new PayInAPIModule(instance.hostName,
                        instance.token))
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .balanceAPIModule(new BalanceAPIModule(instance.hostName,
                        instance.token))
                .secure3DUIModule(new Secure3DUIModule(secure3DUI))
                .build();
    }

    public static PayInViewComponent createPayInViewComponent(Context context, PayInContract.View v)  {
        return instance.buildPayInViewComponent(context, v);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, PayInActivity.class);
        context.startActivity(intent);
    }

    public String getHostName() {
        return hostName;
    }

    public String getToken() {
        return token;
    }

    public PayInConfiguration getConfiguration() {
        return configuration;
    }
}
