package com.dwaplatform.android.account.payinpayoutfinancialdata;

import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUI;
import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUIModule;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;
import com.dwaplatform.android.iban.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 18/01/18.
 */
@Singleton
@Component (modules = { PayInPayOutFinancialDataUIModule.class,
        IbanUIModule.class,
        PaymentCardUIModule.class

})
public interface PayInPayOutFinancialDataUIComponent {
    PayInPayOutFinancialDataUI getFinancialDataUI();
}
