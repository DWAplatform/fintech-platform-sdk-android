package com.dwaplatform.android.account.financialdata.ui;

import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.iban.ui.IbanUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 18/01/18.
 */
@Module
public class FinancialDataUIModule {

    @Provides
    @Singleton
    FinancialDataUI providesFinancialDataUI(IbanUI ibanUI, PaymentCardUI paymentCardUI) {
        return new FinancialDataUI(ibanUI, paymentCardUI);
    }
}
