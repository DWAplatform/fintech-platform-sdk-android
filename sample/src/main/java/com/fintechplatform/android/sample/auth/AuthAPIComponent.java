package com.fintechplatform.android.sample.auth;

import com.fintechplatform.android.api.net.NetModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.sample.auth.api.AuthenticationAPI;
import com.fintechplatform.android.sample.auth.api.AuthenticationAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 19/12/17.
 */
@Singleton
@Component (modules = {
        AuthenticationAPIModule.class,
        LogModule.class,
        NetModule.class
})
public interface AuthAPIComponent {
    AuthenticationAPI getAuthAPI();
}
