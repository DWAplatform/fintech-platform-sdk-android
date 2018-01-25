package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.account.balance.helpers.BalanceHelper;
import com.dwaplatform.android.card.db.PaymentCardPersistenceDB;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.money.FeeHelper;
import com.dwaplatform.android.money.MoneyHelper;
import com.dwaplatform.android.payin.PayInContract;
import com.dwaplatform.android.payin.PayInPresenter;
import com.dwaplatform.android.payin.api.PayInAPI;

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
