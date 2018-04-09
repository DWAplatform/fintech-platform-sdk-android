package com.fintechplatform.ui.payout.ui;


import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.cashout.api.CashOutAPIModule;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.iban.db.IbanPersistanceDBModule;
import com.fintechplatform.ui.money.FeeHelperModule;
import com.fintechplatform.ui.money.MoneyHelperModule;

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
        IbanAPIModule.class,
        FeeHelperModule.class,
        CashOutAPIModule.class,
        IbanPersistanceDBModule.class,
        IbanUIModule.class
})

public interface PayOutViewComponent {
    void inject(PayOutActivity activity);
}
