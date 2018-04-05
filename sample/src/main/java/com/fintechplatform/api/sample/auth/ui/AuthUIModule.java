package com.fintechplatform.api.sample.auth.ui;

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
    private String tenantid;

    public AuthUIModule(String userid, String hostname, String tenantid) {
        this.userid = userid;
        this.hostname = hostname;
        this.tenantid = tenantid;
    }

    @Provides
    @Singleton
    AuthUI providesAuthUI(){
        return new AuthUI(hostname, userid, tenantid);
    }
}
