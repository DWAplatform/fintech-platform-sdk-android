package com.fintechplatform.android.payout;


import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.iban.ui.IbanUIModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.payout.api.PayOutAPIModule;
import com.fintechplatform.android.payout.ui.PayOutUIModule;

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
