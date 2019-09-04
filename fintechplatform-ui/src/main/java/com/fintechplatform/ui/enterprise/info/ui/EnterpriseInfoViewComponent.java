package com.fintechplatform.ui.enterprise.info.ui;

import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDBModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        EnterpriseInfoPresenterModule.class,
        EnterpriseAPIModule.class,
        NetModule.class,
        LogModule.class,
        AlertHelpersModule.class,
        EnterprisePersistanceDBModule.class
})
public interface EnterpriseInfoViewComponent {
    void inject(EnterpriseInfoActivity activity);
}
