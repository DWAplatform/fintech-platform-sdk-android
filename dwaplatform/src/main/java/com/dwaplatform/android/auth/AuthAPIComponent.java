package com.dwaplatform.android.auth;

import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.api.AuthenticationAPI;
import com.dwaplatform.android.auth.api.AuthenticationAPIModule;
import com.dwaplatform.android.log.LogModule;

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
