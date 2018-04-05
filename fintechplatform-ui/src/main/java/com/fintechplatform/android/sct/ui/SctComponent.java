package com.fintechplatform.android.sct.ui;

import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.money.FeeHelperModule;
import com.fintechplatform.android.money.MoneyHelperModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.sct.api.SctAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 22/02/18.
 */
@Singleton
@Component (modules = {
        SctPresenterModule.class,
        BalanceAPIModule.class,
        SctAPIModule.class,
        BalanceHelperModule.class,
        MoneyHelperModule.class,
        FeeHelperModule.class,
        NetModule.class,
        LogModule.class,
        AlertHelpersModule.class
})
public interface SctComponent {
    void inject(SctActivity activity);
}
