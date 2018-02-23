package com.fintechplatform.android.account.financialdata;

import com.fintechplatform.android.account.financialdata.bank.BankFinancialDataUI;
import com.fintechplatform.android.account.financialdata.bank.BankFinancialDataUIModule;
import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.iban.ui.IbanUIModule;

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
