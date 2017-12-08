package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.money.MoneyHelperModule;
import com.dwaplatform.android.payin.PayInActivity;
import com.dwaplatform.android.payin.api.PayInAPIModule;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Singleton
@Component(modules= {
        PayInPresenterModule.class,
        AlertHelpersModule.class,
        LogModule.class,
        PayInAPIModule.class,
        MoneyHelperModule.class
})
public interface PayInViewComponent {
    void inject(PayInActivity activity);
}
