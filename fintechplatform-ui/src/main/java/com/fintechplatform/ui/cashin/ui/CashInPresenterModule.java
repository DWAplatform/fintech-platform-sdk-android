package com.fintechplatform.ui.cashin.ui;

import com.fintechplatform.api.card.api.PaymentCardAPI;
import com.fintechplatform.api.cashin.api.CashInAPI;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper;
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB;
import com.fintechplatform.ui.cashin.CashInContract;
import com.fintechplatform.ui.cashin.TopUpPresenter;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.money.FeeHelper;
import com.fintechplatform.ui.money.MoneyHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CashInPresenterModule {

    private final CashInContract.View view;
    private final DataAccount configuration;

    public CashInPresenterModule(CashInContract.View view,
                                 DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    CashInContract.Presenter provideCashInPresenter(CashInAPI api,
                                                   PaymentCardAPI apiCard,
                                                   MoneyHelper moneyHelper,
                                                   BalanceHelper balanceHelper,
                                                   FeeHelper feeHelper,
                                                   PaymentCardPersistenceDB persistenceDB) {
        return new TopUpPresenter(configuration, view, api, apiCard, moneyHelper, balanceHelper, feeHelper, persistenceDB);
    }
}
