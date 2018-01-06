package com.dwaplatform.android.payout;


import com.dwaplatform.android.iban.ui.IbanUIModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.payout.ui.PayOutUI;
import com.dwaplatform.android.payout.ui.PayOutUIModule;

public class PayOutBuilder {

    public PayOutUIComponent createPayOutUI(String hostName, DataAccount configuration) {

        return DaggerPayOutUIComponent.builder()
                .payOutUIModule(new PayOutUIModule(hostName, configuration))
                .build();
    }
}
