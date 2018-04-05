package com.fintechplatform.api.enterprise;

import com.fintechplatform.api.enterprise.api.EnterpriseAPI;
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 05/04/18.
 */
@Singleton
@Component (modules = {
        EnterpriseAPIModule.class,
        NetModule.class,
        LogModule.class
})
public interface EnterpriseAPIComponent {
    EnterpriseAPI getEnterpriseAPI();
}
