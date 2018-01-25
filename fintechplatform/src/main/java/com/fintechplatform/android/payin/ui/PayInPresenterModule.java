package com.fintechplatform.android.payin.ui;

import com.fintechplatform.android.account.balance.helpers.BalanceHelper;
import com.fintechplatform.android.card.db.PaymentCardPersistenceDB;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.money.FeeHelper;
import com.fintechplatform.android.money.MoneyHelper;
import com.fintechplatform.android.payin.PayInContract;
import com.fintechplatform.android.payin.PayInPresenter;
import com.fintechplatform.android.payin.api.PayInAPI;

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
                                                  MoneyHelper moneyHelper,
                                                  BalanceHelper balanceHelper,
                                                  FeeHelper feeHelper,
                                                  PaymentCardPersistenceDB persistenceDB) {
        return new PayInPresenter(configuration, view, api, moneyHelper, balanceHelper, feeHelper, persistenceDB);
    }
}
