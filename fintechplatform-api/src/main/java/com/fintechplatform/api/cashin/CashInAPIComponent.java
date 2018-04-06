package com.fintechplatform.api.cashin;

import com.fintechplatform.api.cashin.api.CashInAPI;
import com.fintechplatform.api.cashin.api.CashInAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Singleton
@Component(modules= {
        NetModule.class,
        CashInAPIModule.class,
        LogModule.class
})
public interface CashInAPIComponent {
    CashInAPI getPayInAPI();
}
