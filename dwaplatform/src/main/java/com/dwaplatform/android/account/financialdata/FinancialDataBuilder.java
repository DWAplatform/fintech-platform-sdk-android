package com.dwaplatform.android.account.financialdata;

import com.dwaplatform.android.card.ui.PaymentCardUIModule;
import com.dwaplatform.android.iban.ui.IbanUIModule;
import com.dwaplatform.android.models.DataAccount;

public class FinancialDataBuilder {

    public FinancialDataUIComponent createFinancialsUI(String hostName, DataAccount configuration, boolean isSandbox) {
        return DaggerFinancialDataUIComponent.builder()
                .ibanUIModule(new IbanUIModule(hostName, configuration))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, isSandbox, configuration))
                .build();
    }
}
