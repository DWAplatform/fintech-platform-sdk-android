package com.dwaplatform.android.enterprise.address.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.enterprise.api.EnterpriseAPIModule;
import com.dwaplatform.android.enterprise.db.enterprise.EnterprisePersistanceDBModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.profile.address.ui.AddressActivity;
import com.dwaplatform.android.profile.api.ProfileAPIModule;
import com.dwaplatform.android.profile.db.user.UsersPersistanceDBModule;

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
