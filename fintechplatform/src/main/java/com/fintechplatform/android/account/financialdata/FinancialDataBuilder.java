package com.fintechplatform.android.account.financialdata;

import com.fintechplatform.android.account.financialdata.bank.BankFinancialDataUIModule;
import com.fintechplatform.android.account.financialdata.payinpayout.DaggerPayInPayOutFinancialDataUIComponent;
import com.fintechplatform.android.account.financialdata.payinpayout.PayInPayOutFinancialDataUIModule;
import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.iban.ui.IbanUIModule;
import com.fintechplatform.android.models.DataAccount;

public class FinancialDataBuilder {

    public PayInPayOutFinancialDataUIComponent createMangoPayFinancialDataUI(String hostName, DataAccount configuration, boolean isSandbox) {
        return DaggerPayInPayOutFinancialDataUIComponent.builder()
                .ibanUIModule(new IbanUIModule(hostName, configuration))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, isSandbox, configuration))
                .payInPayOutFinancialDataUIModule(new PayInPayOutFinancialDataUIModule(configuration, hostName, isSandbox))
                .build();
    }

    public BankFinancialDataUIComponent createSellaFinancialDataUI(String hostName, DataAccount configuration, boolean isSandbox) {
        return DaggerBankFinancialDataUIComponent.builder()
                .financialDataUIModule(new BankFinancialDataUIModule(configuration, hostName, isSandbox))
                .ibanUIModule(new IbanUIModule(hostName, configuration))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, isSandbox, configuration))
                .build();
    }
}
