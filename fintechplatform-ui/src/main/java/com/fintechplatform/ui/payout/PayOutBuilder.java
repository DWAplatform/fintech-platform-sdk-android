package com.fintechplatform.ui.payout;


import android.content.Context;

import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.payout.ui.PayOutUIModule;

public class PayOutBuilder {


    public PayOutUIComponent createPayOutUI(String hostName, DataAccount configuration, Context context) {

        return DaggerPayOutUIComponent.builder()
                .payOutUIModule(new PayOutUIModule(hostName, configuration))
                .build();
    }
}
