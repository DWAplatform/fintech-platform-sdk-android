package com.fintechplatform.ui.payin;

import com.fintechplatform.ui.card.ui.PaymentCardUIModule;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.payin.ui.PayInUIModule;

public class PayInBuilder {


    public PayInUIComponent createPayInUIComponent(String hostName, boolean sandbox, DataAccount configuration) {
        return DaggerPayInUIComponent.builder()
                .payInUIModule(new PayInUIModule(hostName,
                        configuration, sandbox))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, sandbox, configuration))
                .build();
    }
}