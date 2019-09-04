package com.fintechplatform.ui.payout.ui;


import com.fintechplatform.api.cashout.api.CashOutAPI;
import com.fintechplatform.api.iban.api.IbanAPI;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper;
import com.fintechplatform.ui.iban.db.IbanPersistanceDB;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.money.FeeHelper;
import com.fintechplatform.ui.money.MoneyHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PayOutPresenterModule {
    private final PayOutContract.View view;
    private DataAccount config;

    public PayOutPresenterModule(PayOutContract.View view, DataAccount config) {
        this.view = view;
        this.config = config;
    }

    @Provides
    @Singleton
    PayOutContract.Presenter providePayOutPresenter(CashOutAPI api,
                                                    IbanAPI ibanAPI,
                                                    MoneyHelper moneyHelper,
                                                    BalanceHelper balanceHelper,
                                                    FeeHelper feeHelper,
                                                    IbanPersistanceDB ibanPersistanceDB) {
        return new PayOutPresenter(config, view, api, ibanAPI, moneyHelper, balanceHelper, feeHelper, ibanPersistanceDB);
    }
}
