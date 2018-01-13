package com.dwaplatform.android.sample.auth;

import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.sample.auth.api.AuthenticationAPI;
import com.dwaplatform.android.sample.auth.api.AuthenticationAPIModule;

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
