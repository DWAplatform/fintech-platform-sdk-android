package com.dwaplatform.android.payout.ui;


import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.account.balance.helpers.BalanceHelperModule;
import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.iban.db.IbanPersistanceDBModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.money.FeeHelperModule;
import com.dwaplatform.android.money.MoneyHelperModule;
import com.dwaplatform.android.payout.api.PayOutAPI;
import com.dwaplatform.android.payout.api.PayOutAPIModule;

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
        KeyChainModule.class,
        IbanPersistanceDBModule.class
})

public interface PayOutViewComponent {
    void inject(PayOutActivity activity);
}
