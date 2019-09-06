package com.fintechplatform.ui.account.financialdata;

import com.fintechplatform.ui.account.financialdata.payinpayout.PayInPayOutFinancialDataUI;
import com.fintechplatform.ui.account.financialdata.payinpayout.PayInPayOutFinancialDataUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        PayInPayOutFinancialDataUIModule.class
})
public interface PayInPayOutFinancialDataUIComponent {
    PayInPayOutFinancialDataUI getPayInPayOutFinancialDataUI();
}
