package com.fintechplatform.ui.account.financialdata;

import com.fintechplatform.ui.account.financialdata.bank.BankFinancialDataUI;
import com.fintechplatform.ui.account.financialdata.bank.BankFinancialDataUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        BankFinancialDataUIModule.class
})
public interface BankFinancialDataUIComponent {
    BankFinancialDataUI getFinancialDataUI();
}
