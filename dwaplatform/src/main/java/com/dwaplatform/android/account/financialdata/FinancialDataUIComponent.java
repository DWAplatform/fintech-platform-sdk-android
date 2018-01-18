package com.dwaplatform.android.account.financialdata;

import com.dwaplatform.android.account.financialdata.ui.FinancialDataUI;
import com.dwaplatform.android.account.financialdata.ui.FinancialDataUIModule;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;
import com.dwaplatform.android.iban.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 18/01/18.
 */
@Singleton
@Component (modules = { FinancialDataUIModule.class,
        IbanUIModule.class,
        PaymentCardUIModule.class

})
public interface FinancialDataUIComponent {
    FinancialDataUI getFinancialDataUI();
}
