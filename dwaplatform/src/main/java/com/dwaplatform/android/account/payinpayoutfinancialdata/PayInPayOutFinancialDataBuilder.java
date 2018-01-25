package com.dwaplatform.android.account.payinpayoutfinancialdata;

import com.dwaplatform.android.account.financialdata.DaggerFinancialDataUIComponent;
import com.dwaplatform.android.account.financialdata.FinancialDataUIComponent;
import com.dwaplatform.android.account.financialdata.ui.FinancialDataUIModule;
import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUIModule;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;
import com.dwaplatform.android.iban.ui.IbanUIModule;
import com.dwaplatform.android.models.DataAccount;

public class PayInPayOutFinancialDataBuilder {

    public PayInPayOutFinancialDataUIComponent createFinancialsUI(String hostName, DataAccount configuration, boolean isSandbox) {
        return DaggerPayInPayOutFinancialDataUIComponent.builder()
                .ibanUIModule(new IbanUIModule(hostName, configuration))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, isSandbox, configuration))
                .payInPayOutFinancialDataUIModule(new PayInPayOutFinancialDataUIModule(configuration, hostName, isSandbox))
                .build();
    }

    public FinancialDataUIComponent createFinancialBankInfo(String hostName, DataAccount configuration, boolean isSandbox) {
        return DaggerFinancialDataUIComponent.builder()
                .financialDataUIModule(new FinancialDataUIModule(configuration, hostName, isSandbox))
                .ibanUIModule(new IbanUIModule(hostName, configuration))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, isSandbox, configuration))
                .payInPayOutFinancialDataUIModule(new PayInPayOutFinancialDataUIModule(configuration, hostName, isSandbox))
                .build();
    }
}
