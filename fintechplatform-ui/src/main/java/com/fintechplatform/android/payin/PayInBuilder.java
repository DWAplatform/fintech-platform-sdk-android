package com.fintechplatform.android.payin;

import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.payin.ui.PayInUIModule;

public class PayInBuilder {


    public PayInUIComponent createPayInUIComponent(String hostName, boolean sandbox, DataAccount configuration) {
        return DaggerPayInUIComponent.builder()
                .payInUIModule(new PayInUIModule(hostName,
                        configuration, sandbox))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, sandbox, configuration))
                .build();
    }
}