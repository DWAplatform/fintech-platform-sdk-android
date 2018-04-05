package com.fintechplatform.android.enterprise;

import com.fintechplatform.android.enterprise.api.EnterpriseAPI;
import com.fintechplatform.android.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;

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
