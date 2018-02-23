package com.fintechplatform.android.account.financialdata;

import com.fintechplatform.android.account.financialdata.payinpayout.PayInPayOutFinancialDataUI;
import com.fintechplatform.android.account.financialdata.payinpayout.PayInPayOutFinancialDataUIModule;
import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.iban.ui.IbanUIModule;

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
