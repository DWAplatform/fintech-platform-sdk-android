package com.fintechplatform.ui.sct.ui;

import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.ui.money.FeeHelperModule;
import com.fintechplatform.ui.money.MoneyHelperModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.sct.api.SctAPIModule;

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
