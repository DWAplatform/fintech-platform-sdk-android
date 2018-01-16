package com.dwaplatform.android.sample.auth.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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
