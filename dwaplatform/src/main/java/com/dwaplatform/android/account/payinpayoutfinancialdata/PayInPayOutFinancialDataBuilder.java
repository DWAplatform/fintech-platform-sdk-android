package com.dwaplatform.android.account.payinpayoutfinancialdata;

import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.DaggerPayInPayOutFinancialDataViewComponent;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;
import com.dwaplatform.android.iban.ui.IbanUIModule;
import com.dwaplatform.android.models.DataAccount;

public class PayInPayOutFinancialDataBuilder {

    public PayInPayOutFinancialDataUIComponent createFinancialsUI(String hostName, DataAccount configuration, boolean isSandbox) {
        return DaggerPayInPayOutFinancialDataUIComponent.builder()
                .ibanUIModule(new IbanUIModule(hostName, configuration))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, isSandbox, configuration))
                .build();
    }
}
