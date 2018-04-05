package com.fintechplatform.android.sct.ui;

import com.fintechplatform.android.account.balance.api.BalanceAPI;
import com.fintechplatform.android.account.balance.helpers.BalancePersistence;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.money.FeeHelper;
import com.fintechplatform.android.money.MoneyHelper;
import com.fintechplatform.android.sct.api.SctAPI;

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
