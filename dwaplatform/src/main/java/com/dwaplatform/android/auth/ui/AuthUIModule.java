package com.dwaplatform.android.auth.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 19/12/17.
 */
@Module
public class AuthUIModule {
    private String userid;
    private String hostname;

    public AuthUIModule(String userid, String hostname) {
        this.userid = userid;
        this.hostname = hostname;
    }

    @Provides
    @Singleton
    AuthUI providesAuthUI(){
        return new AuthUI(hostname, userid);
    }
}
