package com.fintechplatform.ui.sct.ui;

import com.fintechplatform.api.account.balance.api.BalanceAPI;
import com.fintechplatform.api.sct.api.SctAPI;
import com.fintechplatform.ui.account.balance.helpers.BalancePersistence;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.money.FeeHelper;
import com.fintechplatform.ui.money.MoneyHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SctPresenterModule {
    private SctContract.View view;
    private DataAccount dataAccount;

    public SctPresenterModule(SctContract.View view, DataAccount dataAccount) {
        this.view = view;
        this.dataAccount = dataAccount;
    }

    @Provides
    @Singleton
    SctContract.Presenter providesSctPresenter(SctAPI apiSct, BalanceAPI apiBalance, BalancePersistence balancePersistence,
                                               MoneyHelper moneyHelper,
                                               FeeHelper feeHelper) {
        return new SctPresenter(view, apiSct, apiBalance, dataAccount, balancePersistence, moneyHelper, feeHelper);
    }
}
