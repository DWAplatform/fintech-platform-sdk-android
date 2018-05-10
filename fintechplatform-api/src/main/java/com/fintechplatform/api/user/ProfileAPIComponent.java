package com.fintechplatform.api.user;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.user.api.ProfileAPI;
import com.fintechplatform.api.user.api.ProfileAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 04/04/18.
 */
@Singleton
@Component (modules = {
        ProfileAPIModule.class,
        NetModule.class,
        LogModule.class
})

public interface ProfileAPIComponent {
    ProfileAPI getProfileAPI();
}
