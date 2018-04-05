package com.fintechplatform.android.profile;

import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.profile.api.ProfileAPI;
import com.fintechplatform.android.profile.api.ProfileAPIModule;

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
