package com.dwaplatform.android.auth.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.api.AuthenticationAPIModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.email.SendEmailHelperModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 19/12/17.
 */
@Singleton
@Component (modules = { AuthenticationPresenterModule.class,
        AuthenticationAPIModule.class,
        NetModule.class,
        KeyChainModule.class,
        LogModule.class,
        AlertHelpersModule.class,
        SendEmailHelperModule.class
})
public interface AuthViewComponent {
    void inject(AuthenticationActivity activity);
}
