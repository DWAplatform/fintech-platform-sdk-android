package com.dwaplatform.android.sample.auth.ui;

import com.dwaplatform.android.log.Log;
import com.dwaplatform.android.sample.auth.api.AuthenticationAPI;
import com.dwaplatform.android.sample.auth.keys.KeyChain;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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
