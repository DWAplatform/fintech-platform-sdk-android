package com.fintechplatform.android.payin.ui;

import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.card.db.PaymentCardPersistanceModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.money.FeeHelperModule;
import com.fintechplatform.android.money.MoneyHelperModule;
import com.fintechplatform.android.payin.PayInActivity;
import com.fintechplatform.android.payin.api.PayInAPIModule;

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
        FeeHelperModule.class,
        NetModule.class,
        Secure3DUIHelperModule.class,
        PaymentCardUIModule.class
})
public interface PayInViewComponent {
    void inject(PayInActivity activity);
}
