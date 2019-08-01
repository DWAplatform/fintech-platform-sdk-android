package com.fintechplatform.ui.cashin;

import com.fintechplatform.ui.card.ui.PaymentCardUIModule;
import com.fintechplatform.ui.cashin.ui.CashInUIModule;
import com.fintechplatform.ui.models.DataAccount;

public class CashInBuilder {
    public CashInUIComponent createCashInUIComponent(String hostName, boolean sandbox, DataAccount configuration) {
        return DaggerCashInUIComponent.builder()
                .cashInUIModule(new CashInUIModule(hostName,
                        configuration, sandbox))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, sandbox, configuration))
                .build();
    }
}