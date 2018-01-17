package com.dwaplatform.android.payout;


import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.iban.ui.IbanUIModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.payout.api.PayOutAPIModule;
import com.dwaplatform.android.payout.ui.PayOutUIModule;

public class PayOutBuilder {

    public PayOutAPIComponent createPayOutAPI(Context context, String hostName) {
        return DaggerPayOutAPIComponent.builder()
                .payOutAPIModule(new PayOutAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

    public PayOutUIComponent createPayOutUI(String hostName, DataAccount configuration) {

        return DaggerPayOutUIComponent.builder()
                .payOutUIModule(new PayOutUIModule(hostName, configuration))
                .ibanUIModule(new IbanUIModule(hostName, configuration))
                .build();
    }
}
