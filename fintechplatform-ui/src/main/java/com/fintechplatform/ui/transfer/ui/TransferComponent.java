package com.fintechplatform.ui.transfer.ui;

import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.transfer.api.TransferAPIModule;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.images.ImageHelperModule;
import com.fintechplatform.ui.money.FeeHelperModule;
import com.fintechplatform.ui.money.MoneyHelperModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {TransferPresenterModule.class,
        TransferAPIModule.class,
        BalanceAPIModule.class,
        AlertHelpersModule.class,
        //NetworkUsersPersistanceModule.class,
        BalanceHelperModule.class,
        MoneyHelperModule.class,
        FeeHelperModule.class,
        ImageHelperModule.class,
        LogModule.class,
        NetModule.class})
public interface TransferComponent {
    void inject(TransferActivity activity);
}
