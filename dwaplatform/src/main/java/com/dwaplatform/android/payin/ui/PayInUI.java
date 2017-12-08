package com.dwaplatform.android.payin.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.payin.PayInActivity;
import com.dwaplatform.android.payin.PayInContract;
import com.dwaplatform.android.payin.PayInUIComponent;
import com.dwaplatform.android.payin.api.PayInAPIModule;
import com.dwaplatform.android.payin.models.PayInConfiguration;

/**
 * Created by tcappellari on 08/12/2017.
 */

public class PayInUI {

    String hostName;
    String token;
    PayInConfiguration configuration;

    static PayInUI instance;

    public PayInUI(String hostName, String token, PayInConfiguration configuration) {
        this.hostName = hostName;
        this.token = token;
        this.configuration = configuration;
    }


    public static PayInViewComponent createPayInViewComponent(Context context, PayInContract.View v)  {
        return DaggerPayInUIComponent.builder()
                .payInPresenterModule(new PayInPresenterModule(v, instance.configuration)
                        .payInAPIModule(new PayInAPIModule(instance.hostName,
                                instance.token,
                                Volley.newRequestQueue(context)))
                )
                .build();
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, PayInActivity.class);
        context.startActivity(intent);
    }
}
