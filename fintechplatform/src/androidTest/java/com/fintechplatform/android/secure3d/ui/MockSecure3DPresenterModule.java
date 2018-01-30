package com.fintechplatform.android.secure3d.ui;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockSecure3DPresenterModule {

    @Singleton
    @Provides
    Secure3DContract.View providesSecure3DView(){
        return Mockito.mock(Secure3DContract.View.class);
    }

    @Singleton
    @Provides
    Secure3DContract.Presenter providesSecure3DPresenter(){
        return Mockito.mock(Secure3DContract.Presenter.class);
    }
}