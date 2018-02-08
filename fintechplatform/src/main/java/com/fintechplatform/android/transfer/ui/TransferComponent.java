package com.fintechplatform.android.transfer.ui;

import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.images.ImageHelperModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.money.FeeHelperModule;
import com.fintechplatform.android.money.MoneyHelperModule;
import com.fintechplatform.android.transfer.api.TransferAPIModule;

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
