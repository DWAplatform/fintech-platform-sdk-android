package com.fintechplatform.android.enterprise.address.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.android.enterprise.db.enterprise.EnterprisePersistanceDBModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        EnterpriseAddressPresenterModule.class,
        EnterpriseAPIModule.class,
        NetModule.class,
        LogModule.class,
        AlertHelpersModule.class,
        EnterprisePersistanceDBModule.class
})
public interface EnterpriseAddressViewComponent {
    void inject(EnterpriseAddressActivity activity);
}
