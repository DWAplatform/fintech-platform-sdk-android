package com.fintechplatform.ui.account.financialdata;

import com.fintechplatform.ui.account.financialdata.bank.BankFinancialDataUI;
import com.fintechplatform.ui.account.financialdata.bank.BankFinancialDataUIModule;
import com.fintechplatform.ui.card.ui.PaymentCardUIModule;
import com.fintechplatform.ui.iban.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        BankFinancialDataUIModule.class,
        IbanUIModule.class,
        PaymentCardUIModule.class})
public interface BankFinancialDataUIComponent {
    BankFinancialDataUI getFinancialDataUI();
}
