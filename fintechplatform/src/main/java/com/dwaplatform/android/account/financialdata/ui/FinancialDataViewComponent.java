package com.dwaplatform.android.account.financialdata.ui;

import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataPresenterModule;
import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.api.PaymentCardAPIModule;
import com.dwaplatform.android.card.db.PaymentCardPersistanceModule;
import com.dwaplatform.android.iban.api.IbanAPIModule;
import com.dwaplatform.android.iban.db.IbanPersistanceDBModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.payin.ui.PaymentCardUIModule;
import com.dwaplatform.android.payout.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
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
public interface FinancialDataViewComponent {
    void inject(FinancialDataActivity activity);
}
