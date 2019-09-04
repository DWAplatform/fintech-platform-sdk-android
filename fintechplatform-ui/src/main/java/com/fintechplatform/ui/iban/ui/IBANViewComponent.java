package com.fintechplatform.ui.iban.ui;


import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.profile.api.ProfileAPIModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDBModule;
import com.fintechplatform.ui.iban.db.IbanPersistanceDBModule;
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDBModule;

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
