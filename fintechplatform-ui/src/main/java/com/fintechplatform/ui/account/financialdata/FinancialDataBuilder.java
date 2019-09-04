package com.fintechplatform.ui.account.financialdata;

import com.fintechplatform.ui.account.financialdata.bank.BankFinancialDataUIModule;
import com.fintechplatform.ui.account.financialdata.payinpayout.PayInPayOutFinancialDataUIModule;
import com.fintechplatform.ui.models.DataAccount;

public class FinancialDataBuilder {

    public PayInPayOutFinancialDataUIComponent createMangoPayFinancialDataUI(String hostName, DataAccount configuration, boolean isSandbox) {
        return DaggerPayInPayOutFinancialDataUIComponent.builder()
                //.paymentCardUIModule(new PaymentCardUIModule(hostName, isSandbox, configuration))
                .payInPayOutFinancialDataUIModule(new PayInPayOutFinancialDataUIModule(configuration, hostName, isSandbox))
                .build();
    }

    public BankFinancialDataUIComponent createSellaFinancialDataUI(String hostName, DataAccount configuration, boolean isSandbox) {
        return DaggerBankFinancialDataUIComponent.builder()
                .bankFinancialDataUIModule(new BankFinancialDataUIModule(configuration, hostName, isSandbox))
                //.paymentCardUIModule(new PaymentCardUIModule(hostName, isSandbox, configuration))
                .build();
    }
}
