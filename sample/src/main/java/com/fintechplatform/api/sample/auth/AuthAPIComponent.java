package com.fintechplatform.api.sample.auth;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.net.NetModule;
import com.fintechplatform.api.sample.auth.api.AuthenticationAPI;
import com.fintechplatform.api.sample.auth.api.AuthenticationAPIModule;

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
