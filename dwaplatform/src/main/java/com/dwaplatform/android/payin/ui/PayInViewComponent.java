package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.account.balance.helpers.BalanceHelperModule;
import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.models.FeeHelperModule;
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
        FeeHelperModule.class,
        NetModule.class
})
public interface PayInViewComponent {
    void inject(PayInActivity activity);
}
