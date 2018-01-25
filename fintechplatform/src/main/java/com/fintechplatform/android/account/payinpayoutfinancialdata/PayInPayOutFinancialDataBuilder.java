package com.fintechplatform.android.account.payinpayoutfinancialdata;

import com.fintechplatform.android.account.financialdata.DaggerFinancialDataUIComponent;
import com.fintechplatform.android.account.financialdata.FinancialDataUIComponent;
import com.fintechplatform.android.account.financialdata.ui.FinancialDataUIModule;
import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUIModule;
import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.iban.ui.IbanUIModule;
import com.fintechplatform.android.models.DataAccount;

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
