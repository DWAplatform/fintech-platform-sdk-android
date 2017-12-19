package com.dwaplatform.android.auth.ui;

import com.dwaplatform.android.auth.api.AuthenticationAPI;
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

    AuthenticationPresenterModule(AuthenticationContract.View view, String userid) {
        this.view = view;
        this.userid = userid;
    }

    @Provides
    @Singleton
    AuthenticationContract.Presenter providesAuthPresenter(Log log, AuthenticationAPI api){
        return new AuthenticationPresenter(view, log, api, userid);
    }
}
