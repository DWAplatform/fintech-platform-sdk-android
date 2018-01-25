package com.fintechplatform.android.account.payinpayoutfinancialdata.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.card.api.PaymentCardAPIModule;
import com.fintechplatform.android.card.db.PaymentCardPersistanceModule;
import com.fintechplatform.android.iban.api.IbanAPIModule;
import com.fintechplatform.android.iban.db.IbanPersistanceDBModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.payin.ui.PaymentCardUIModule;
import com.fintechplatform.android.payout.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 18/01/18.
 */
@Singleton
@Component (modules = {
        PayInPayOutFinancialDataPresenterModule.class,
        PaymentCardPersistanceModule.class,
        IbanPersistanceDBModule.class,
        AlertHelpersModule.class,
        PaymentCardUIModule.class,
        IbanUIModule.class,
        NetModule.class,
        PaymentCardAPIModule.class,
        IbanAPIModule.class,
        PayInPayOutFinancialDataUIModule.class,
        LogModule.class
})
public interface PayInPayOutFinancialDataViewComponent {
    void inject(PayInPayOutFinancialDataActivity activity);
}
