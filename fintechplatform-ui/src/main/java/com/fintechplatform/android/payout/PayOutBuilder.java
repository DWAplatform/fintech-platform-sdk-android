package com.fintechplatform.android.payout;


import com.fintechplatform.android.iban.ui.IbanUIModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.payout.ui.PayOutUIModule;

public class PayOutBuilder {


    public PayOutUIComponent createPayOutUI(String hostName, DataAccount configuration) {

        return DaggerPayOutUIComponent.builder()
                .payOutUIModule(new PayOutUIModule(hostName, configuration))
                .ibanUIModule(new IbanUIModule(hostName, configuration))
                .build();
    }
}
