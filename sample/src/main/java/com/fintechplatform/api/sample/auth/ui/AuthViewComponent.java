package com.fintechplatform.api.sample.auth.ui;

import com.fintechplatform.api.alert.AlertHelpersModule;
import com.fintechplatform.api.email.SendEmailHelperModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.net.NetModule;
import com.fintechplatform.api.sample.auth.api.AuthenticationAPIModule;
import com.fintechplatform.api.sample.auth.keys.KeyChainModule;

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
