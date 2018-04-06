package com.fintechplatform.ui.cashin.ui;

import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.cashin.api.CashInAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.card.db.PaymentCardPersistanceModule;
import com.fintechplatform.ui.cashin.CashInActivity;
import com.fintechplatform.ui.money.FeeHelperModule;
import com.fintechplatform.ui.money.MoneyHelperModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Singleton
@Component(modules= {
        CashInPresenterModule.class,
        AlertHelpersModule.class,
        LogModule.class,
        CashInAPIModule.class,
        MoneyHelperModule.class,
        BalanceAPIModule.class,
        BalanceHelperModule.class,
        PaymentCardPersistanceModule.class,
        PaymentCardAPIModule.class,
        FeeHelperModule.class,
        NetModule.class,
        Secure3DUIHelperModule.class,
        PaymentCardUIModule.class
})
public interface CashInViewComponent {
    void inject(CashInActivity activity);
}
