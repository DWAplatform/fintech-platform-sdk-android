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
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        IBANActivityModule.class,
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
/*
    @Component.Builder
    interface Builder {
        IBANViewComponent build();

        @BindsInstance
        Builder ibanPresenter(IBANPresenterModule configuration);
        @BindsInstance
        Builder ibanAPIModule(IbanAPIModule data);
        @BindsInstance
        Builder profileAPIModule(ProfileAPIModule hostName);
        @BindsInstance
        Builder enterpriseAPIModule(EnterpriseAPIModule hostName);
        @BindsInstance
        Builder netModule(NetModule data);


    }*/


    void inject(IbanUI ui);
}
