package com.fintechplatform.api.cashin.ui;

import com.fintechplatform.api.cashin.PayInContract;

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
