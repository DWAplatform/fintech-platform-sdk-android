package com.fintechplatform.android.sample.auth.ui;

import com.fintechplatform.android.log.Log;
import com.fintechplatform.android.sample.auth.api.AuthenticationAPI;
import com.fintechplatform.android.sample.auth.keys.KeyChain;

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

    AuthenticationPresenterModule(AuthenticationContract.View view, String userid) {
        this.view = view;
        this.userid = userid;
    }

    @Provides
    @Singleton
    AuthenticationContract.Presenter providesAuthPresenter(Log log, AuthenticationAPI api, KeyChain keyChain){
        return new AuthenticationPresenter(view, log, api, userid, keyChain);
    }
}
