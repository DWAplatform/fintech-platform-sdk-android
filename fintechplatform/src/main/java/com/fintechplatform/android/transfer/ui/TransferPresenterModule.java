package com.fintechplatform.android.transfer.ui;

import com.fintechplatform.android.account.balance.api.BalanceAPI;
import com.fintechplatform.android.account.balance.helpers.BalancePersistence;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.money.FeeHelper;
import com.fintechplatform.android.money.MoneyHelper;
import com.fintechplatform.android.transfer.api.TransferAPI;
import com.fintechplatform.android.transfer.contactslist.db.NetworkUsersPersistance;

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
                                                        NetworkUsersPersistance contanctsPersistance,
                                                        MoneyHelper moneyHelper,
                                                        FeeHelper feeHelper){
        return new TransferPresenter(view, apiTransfer, apiBalance, dataAccount, balancePersistence, contanctsPersistance, moneyHelper, feeHelper);
    }
}
