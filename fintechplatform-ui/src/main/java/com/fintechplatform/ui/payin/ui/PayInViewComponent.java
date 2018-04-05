package com.fintechplatform.ui.payin.ui;

import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.ui.card.db.PaymentCardPersistanceModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.ui.money.FeeHelperModule;
import com.fintechplatform.ui.money.MoneyHelperModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.payin.PayInActivity;
import com.fintechplatform.api.payin.api.PayInAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Singleton
@Component(modules= {
        PayInPresenterModule.class,
        AlertHelpersModule.class,
        LogModule.class,
        PayInAPIModule.class,
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
public interface PayInViewComponent {
    void inject(PayInActivity activity);
}
