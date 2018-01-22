package com.dwaplatform.android.enterprise.info.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.enterprise.api.EnterpriseAPIModule;
import com.dwaplatform.android.enterprise.db.enterprise.EnterprisePersistanceDBModule;
import com.dwaplatform.android.log.LogModule;

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
