package com.fintechplatform.android.enterprise.contacts.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.android.enterprise.db.enterprise.EnterprisePersistanceDBModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        EnterpriseContactsPresenterModule.class,
        AlertHelpersModule.class,
        EnterpriseAPIModule.class,
        NetModule.class,
        EnterprisePersistanceDBModule.class,
        LogModule.class
})
public interface EnterpriseContactsViewComponent {
    void inject(EnterpriseContactsActivity activity);
}
