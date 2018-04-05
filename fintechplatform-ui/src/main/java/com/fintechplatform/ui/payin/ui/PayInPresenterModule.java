package com.fintechplatform.ui.payin.ui;

import com.fintechplatform.ui.account.balance.helpers.BalanceHelper;
import com.fintechplatform.api.card.api.PaymentCardRestAPI;
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.money.FeeHelper;
import com.fintechplatform.ui.money.MoneyHelper;
import com.fintechplatform.ui.payin.PayInContract;
import com.fintechplatform.ui.payin.PayInPresenter;
import com.fintechplatform.api.payin.api.PayInAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PayInPresenterModule {

    private final PayInContract.View view;
    private final DataAccount configuration;

    public PayInPresenterModule(PayInContract.View view,
                                DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    PayInContract.Presenter providePayInPresenter(PayInAPI api,
                                                  PaymentCardRestAPI apiCard,
                                                  MoneyHelper moneyHelper,
                                                  BalanceHelper balanceHelper,
                                                  FeeHelper feeHelper,
                                                  PaymentCardPersistenceDB persistenceDB) {
        return new PayInPresenter(configuration, view, api, apiCard, moneyHelper, balanceHelper, feeHelper, persistenceDB);
    }
}
