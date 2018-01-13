package com.dwaplatform.android.iban.ui;


import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.iban.api.IbanAPIModule;
import com.dwaplatform.android.iban.db.IbanPersistanceDBModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.profile.db.user.UsersPersistanceDBModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        IBANPresenterModule.class,
        IbanAPIModule.class,
        IbanUIModule.class,
        AlertHelpersModule.class,
        NetModule.class,
        LogModule.class,
        UsersPersistanceDBModule.class,
        IbanPersistanceDBModule.class
})
public interface IBANViewComponent {
    void inject(IBANActivity activity);
}
