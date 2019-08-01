package com.fintechplatform.ui.iban.ui;


import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.profile.api.ProfileAPIModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDBModule;
import com.fintechplatform.ui.iban.db.IbanPersistanceDBModule;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDBModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
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

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder ibanPresenter(DataAccount configuration);

        @BindsInstance
        Builder ibanAPIModule(NetData data);
        @BindsInstance
        Builder profileAPIModule(String hostName);
        @BindsInstance
        Builder enterpriseAPIModule(String hostName);
        @BindsInstance
        Builder netModule(NetData data);

        IBANViewComponent build();
    }


    void inject(IbanUI ui);
}
