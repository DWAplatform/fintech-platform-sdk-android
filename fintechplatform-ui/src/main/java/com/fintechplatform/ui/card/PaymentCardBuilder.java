package com.fintechplatform.ui.card;

import com.fintechplatform.ui.card.ui.PaymentCardUIModule;
import com.fintechplatform.ui.models.DataAccount;

public class PaymentCardBuilder {

    public PaymentCardUIComponent createPaymentCardUI(String hostname, boolean sandbox, DataAccount configuration) {
        return DaggerPaymentCardUIComponent.builder()
                .paymentCardUIModule(new PaymentCardUIModule(hostname, sandbox, configuration))
                .build();
    }
}
