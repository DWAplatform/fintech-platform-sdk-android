package com.dwaplatform.android.enterprise.contacts.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.enterprise.api.EnterpriseAPIModule;
import com.dwaplatform.android.enterprise.db.enterprise.EnterprisePersistanceDBModule;
import com.dwaplatform.android.log.LogModule;

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
