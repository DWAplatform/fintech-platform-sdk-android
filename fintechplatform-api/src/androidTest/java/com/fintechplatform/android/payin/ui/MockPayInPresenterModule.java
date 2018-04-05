package com.fintechplatform.android.payin.ui;

import com.fintechplatform.android.payin.PayInContract;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockPayInPresenterModule {

    @Provides
    @Singleton
    PayInContract.View providesPayInView(){
        return Mockito.mock(PayInContract.View.class);
    }

    @Provides
    @Singleton
    PayInContract.Presenter providesPayInPresenter() {
        return Mockito.mock(PayInContract.Presenter.class);
    }
}
