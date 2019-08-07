package com.fintechplatform.ui.payout;


import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.payout.ui.PayOutUIModule;

public class PayOutBuilder {


    public PayOutUIComponent createPayOutUI(String hostName, DataAccount configuration) {

        return DaggerPayOutUIComponent.builder()
                .payOutUIModule(new PayOutUIModule(hostName, configuration))
                .build();
    }
}
