package com.fintechplatform.android.account.financialdata;

import com.fintechplatform.android.account.financialdata.ui.FinancialDataUI;
import com.fintechplatform.android.account.financialdata.ui.FinancialDataUIModule;
import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUIModule;
import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.iban.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = { FinancialDataUIModule.class,
        PayInPayOutFinancialDataUIModule.class,
        IbanUIModule.class,
        PaymentCardUIModule.class})
public interface FinancialDataUIComponent {
    FinancialDataUI getFinancialDataUI();
}
