package com.fintechplatform.android.sample.auth.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.email.SendEmailHelperModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.net.NetModule;
import com.fintechplatform.android.sample.auth.api.AuthenticationAPIModule;
import com.fintechplatform.android.sample.auth.keys.KeyChainModule;

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
