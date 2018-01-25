package com.fintechplatform.android.account.payinpayoutfinancialdata;

import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUI;
import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUIModule;
import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.iban.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 18/01/18.
 */
@Singleton
@Component (modules = {
        PayInPayOutFinancialDataUIModule.class,
        IbanUIModule.class,
        PaymentCardUIModule.class

})
public interface PayInPayOutFinancialDataUIComponent {
    PayInPayOutFinancialDataUI getFinancialDataUI();
}
