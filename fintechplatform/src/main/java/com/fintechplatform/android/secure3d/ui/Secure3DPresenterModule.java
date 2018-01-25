package com.fintechplatform.android.secure3d.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 12/12/17.
 */
@Module
public class Secure3DPresenterModule {

    private final Secure3DContract.View view;

    public Secure3DPresenterModule(Secure3DContract.View view) {
        this.view = view;
    }

    @Singleton
    @Provides
    Secure3DContract.Presenter providesSecure3DPresenter(){
        return new Secure3DPresenter(view);
    }
}