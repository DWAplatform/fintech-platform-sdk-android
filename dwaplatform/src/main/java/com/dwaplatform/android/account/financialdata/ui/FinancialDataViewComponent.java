package com.dwaplatform.android.account.financialdata.ui;

import com.dwaplatform.android.account.financialdata.ui.FinancialDataActivity;
import com.dwaplatform.android.account.financialdata.ui.FinancialDataPresenterModule;
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
        FinancialDataPresenterModule.class,
        PaymentCardPersistanceModule.class,
        IbanPersistanceDBModule.class,
        AlertHelpersModule.class,
        PaymentCardUIModule.class,
        IbanUIModule.class
})
public interface FinancialDataViewComponent {
    void inject(FinancialDataActivity activity);
}
