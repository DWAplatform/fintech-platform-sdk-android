package com.fintechplatform.android.card;

import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.models.DataAccount;

public class PaymentCardBuilder {

    public PaymentCardUIComponent createPaymentCardUI(String hostname, boolean sandbox, DataAccount configuration) {
        return DaggerPaymentCardUIComponent.builder()
                .paymentCardUIModule(new PaymentCardUIModule(hostname, sandbox, configuration))
                .build();
    }
}
