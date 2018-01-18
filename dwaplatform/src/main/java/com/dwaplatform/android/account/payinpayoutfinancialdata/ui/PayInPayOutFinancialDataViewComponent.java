package com.dwaplatform.android.account.payinpayoutfinancialdata.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.card.db.PaymentCardPersistanceModule;
import com.dwaplatform.android.iban.db.IbanPersistanceDBModule;
import com.dwaplatform.android.payin.ui.PaymentCardUIModule;
import com.dwaplatform.android.payout.ui.IbanUIModule;

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
        IbanUIModule.class
})
public interface PayInPayOutFinancialDataViewComponent {
    void inject(PayInPayOutFinancialDataActivity activity);
}
