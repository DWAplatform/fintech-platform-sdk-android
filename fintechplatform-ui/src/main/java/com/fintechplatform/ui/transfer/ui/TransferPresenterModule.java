package com.fintechplatform.ui.transfer.ui;

import com.fintechplatform.api.account.balance.api.BalanceAPI;
import com.fintechplatform.ui.account.balance.helpers.BalancePersistence;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.money.FeeHelper;
import com.fintechplatform.ui.money.MoneyHelper;
import com.fintechplatform.api.transfer.api.TransferAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransferPresenterModule {

    TransferContract.View view;
    private DataAccount dataAccount;

    public TransferPresenterModule(TransferContract.View view, DataAccount dataAccount) {
        this.view = view;
        this.dataAccount = dataAccount;
    }

    @Singleton
    @Provides
    TransferContract.Presenter providesTrasferPresenter(TransferAPI apiTransfer,
                                                        BalanceAPI apiBalance,
                                                        BalancePersistence balancePersistence,
                                                        MoneyHelper moneyHelper,
                                                        FeeHelper feeHelper){
        return new TransferPresenter(view, apiTransfer, apiBalance, dataAccount, balancePersistence, moneyHelper, feeHelper);
    }
}
