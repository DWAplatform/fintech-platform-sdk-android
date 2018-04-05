package com.fintechplatform.ui.account.financialdata;

import com.fintechplatform.ui.account.financialdata.payinpayout.PayInPayOutFinancialDataUI;
import com.fintechplatform.ui.account.financialdata.payinpayout.PayInPayOutFinancialDataUIModule;
import com.fintechplatform.ui.card.ui.PaymentCardUIModule;
import com.fintechplatform.ui.iban.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        PayInPayOutFinancialDataUIModule.class,
        IbanUIModule.class,
        PaymentCardUIModule.class

})
public interface PayInPayOutFinancialDataUIComponent {
    PayInPayOutFinancialDataUI getPayInPayOutFinancialDataUI();
}
