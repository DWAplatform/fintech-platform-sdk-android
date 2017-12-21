package com.dwaplatform.android.auth.ui;

import android.app.Activity;
import android.content.Intent;

import com.dwaplatform.android.auth.api.AuthenticationAPI;
import com.dwaplatform.android.auth.keys.KeyChain;
import com.dwaplatform.android.log.Log;

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
    private Intent activity;

    AuthenticationPresenterModule(AuthenticationContract.View view, String userid, Intent activity) {
        this.view = view;
        this.userid = userid;
        this.activity = activity;
    }

    @Provides
    @Singleton
    AuthenticationContract.Presenter providesAuthPresenter(Log log, AuthenticationAPI api, KeyChain keyChain){
        return new AuthenticationPresenter(view, log, api, userid, activity, keyChain);
    }
}
