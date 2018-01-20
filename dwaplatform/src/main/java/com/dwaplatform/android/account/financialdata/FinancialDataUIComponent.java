package com.dwaplatform.android.account.financialdata;

import com.dwaplatform.android.account.financialdata.ui.FinancialDataActivity;
import com.dwaplatform.android.account.financialdata.ui.FinancialDataUI;
import com.dwaplatform.android.account.financialdata.ui.FinancialDataUIModule;
import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUIModule;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;
import com.dwaplatform.android.iban.ui.IbanUIModule;

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
