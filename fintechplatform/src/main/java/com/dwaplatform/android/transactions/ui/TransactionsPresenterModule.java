package com.dwaplatform.android.transactions.ui;

import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.transactions.api.TransactionsAPI;
import com.dwaplatform.android.transactions.db.TransactionPersistanceDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionsPresenterModule {

    private TransactionsContract.View view;
    private DataAccount configuration;

    public TransactionsPresenterModule(TransactionsContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    TransactionsContract.Presenter providesTransactionsPresenter(TransactionsAPI api,
                                                                 TransactionPersistanceDB transactionsPersistance) {
        return new TransactionsPresenter(view, api, configuration, transactionsPersistance);
    }
}
