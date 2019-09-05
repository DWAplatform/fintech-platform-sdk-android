package com.fintechplatform.api.payin;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.payin.api.PayInAPI;
import com.fintechplatform.api.payin.api.PayInAPIModule;

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
public interface PAyInAPIComponent {
    PayInAPI getPayInAPI();
}
