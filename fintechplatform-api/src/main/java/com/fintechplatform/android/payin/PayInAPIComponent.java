package com.fintechplatform.android.payin;

import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.payin.api.PayInAPI;
import com.fintechplatform.android.payin.api.PayInAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Singleton
@Component(modules= {
        NetModule.class,
        PayInAPIModule.class,
        LogModule.class
})
public interface PayInAPIComponent {
    PayInAPI getPayInAPI();
}
