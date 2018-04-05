package com.fintechplatform.android.iban.ui;


import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.android.enterprise.db.enterprise.EnterprisePersistanceDBModule;
import com.fintechplatform.android.iban.api.IbanAPIModule;
import com.fintechplatform.android.iban.db.IbanPersistanceDBModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.profile.api.ProfileAPIModule;
import com.fintechplatform.android.profile.db.user.UsersPersistanceDBModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        IBANPresenterModule.class,
        IbanAPIModule.class,
        IbanUIModule.class,
        AlertHelpersModule.class,
        ProfileAPIModule.class,
        EnterpriseAPIModule.class,
        NetModule.class,
        LogModule.class,
        UsersPersistanceDBModule.class,
        EnterprisePersistanceDBModule.class,
        IbanPersistanceDBModule.class
})
public interface IBANViewComponent {
    void inject(IBANActivity activity);
}
