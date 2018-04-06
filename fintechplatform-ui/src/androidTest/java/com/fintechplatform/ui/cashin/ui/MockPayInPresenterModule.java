package com.fintechplatform.ui.cashin.ui;

import com.fintechplatform.ui.cashin.CashInContract;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockPayInPresenterModule {

    @Provides
    @Singleton
    CashInContract.View providesPayInView(){
        return Mockito.mock(CashInContract.View.class);
    }

    @Provides
    @Singleton
    CashInContract.Presenter providesPayInPresenter() {
        return Mockito.mock(CashInContract.Presenter.class);
    }
}
