package com.fintechplatform.api.sample.auth.ui;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.sample.auth.api.AuthenticationAPI;
import com.fintechplatform.api.sample.auth.keys.KeyChain;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 19/12/17.
 */
@Module
public class AuthenticationPresenterModule {

    private AuthenticationContract.View view;
    private String userid;
    private String tenantid;

    AuthenticationPresenterModule(AuthenticationContract.View view, String userid, String tenantid) {
        this.view = view;
        this.userid = userid;
        this.tenantid = tenantid;
    }

    @Provides
    @Singleton
    AuthenticationContract.Presenter providesAuthPresenter(Log log, AuthenticationAPI api, KeyChain keyChain){
        return new AuthenticationPresenter(view, log, api, userid, tenantid, keyChain);
    }
}
