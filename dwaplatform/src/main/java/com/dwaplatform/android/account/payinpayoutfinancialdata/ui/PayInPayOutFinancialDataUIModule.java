package com.dwaplatform.android.account.payinpayoutfinancialdata.ui;

import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.iban.ui.IbanUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 18/01/18.
 */
@Module
public class PayInPayOutFinancialDataUIModule {

    @Provides
    @Singleton
    PayInPayOutFinancialDataUI providesFinancialDataUI(IbanUI ibanUI, PaymentCardUI paymentCardUI) {
        return new PayInPayOutFinancialDataUI(ibanUI, paymentCardUI);
    }
}
