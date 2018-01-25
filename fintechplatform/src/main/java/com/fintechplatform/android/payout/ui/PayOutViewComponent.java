package com.fintechplatform.android.payout.ui;


import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.iban.db.IbanPersistanceDBModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.money.FeeHelperModule;
import com.fintechplatform.android.money.MoneyHelperModule;
import com.fintechplatform.android.payout.api.PayOutAPIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AlertHelpersModule.class,
        LogModule.class,
        NetModule.class,
        MoneyHelperModule.class,
        PayOutPresenterModule.class,
        BalanceHelperModule.class,
        BalanceAPIModule.class,
        FeeHelperModule.class,
        PayOutAPIModule.class,
        IbanPersistanceDBModule.class,
        IbanUIModule.class
})

public interface PayOutViewComponent {
    void inject(PayOutActivity activity);
}
