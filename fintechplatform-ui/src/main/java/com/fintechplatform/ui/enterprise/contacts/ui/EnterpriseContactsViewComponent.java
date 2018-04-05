package com.fintechplatform.ui.enterprise.contacts.ui;

import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDBModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;

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
