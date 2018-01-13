package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.account.balance.helpers.BalanceHelperModule;
import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.db.PaymentCardPersistanceModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.money.FeeHelperModule;
import com.dwaplatform.android.money.MoneyHelperModule;
import com.dwaplatform.android.payin.PayInActivity;
import com.dwaplatform.android.payin.api.PayInAPIModule;

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
        Secure3DUIModule.class,
        PaymentCardUIModule.class
})
public interface PayInViewComponent {
    void inject(PayInActivity activity);
}
