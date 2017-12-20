package com.dwaplatform.android.auth.ui;

import android.content.Intent;

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
    private Intent intent;

    public AuthUIModule(String userid, String hostname, Intent intent) {
        this.userid = userid;
        this.hostname = hostname;
        this.intent = intent;
    }

    @Provides
    @Singleton
    AuthUI providesAuthUI(){
        return new AuthUI(hostname, userid, intent);
    }
}
