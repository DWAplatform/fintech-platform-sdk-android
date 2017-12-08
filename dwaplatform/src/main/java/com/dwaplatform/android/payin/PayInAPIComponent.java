package com.dwaplatform.android.payin;

import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.payin.api.PayInAPI;
import com.dwaplatform.android.payin.api.PayInAPIModule;
import com.dwaplatform.android.payin.ui.PayInPresenterModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Singleton
@Component(modules= {
        NetModule.class,
        PayInAPIModule.class,
        LogModule.class,
        NetModule.class

})
interface PayInAPIComponent {

    PayInAPI getPayInAPI();
}
