package com.fintechplatform.android.payout.ui;


import com.fintechplatform.android.account.balance.helpers.BalanceHelper;
import com.fintechplatform.android.iban.api.IbanAPI;
import com.fintechplatform.android.iban.db.IbanPersistanceDB;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.money.FeeHelper;
import com.fintechplatform.android.money.MoneyHelper;
import com.fintechplatform.android.payout.api.PayOutAPI;

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
    PayOutContract.Presenter providePayOutPresenter(PayOutAPI api,
                                                    IbanAPI ibanAPI,
                                                    MoneyHelper moneyHelper,
                                                    BalanceHelper balanceHelper,
                                                    FeeHelper feeHelper,
                                                    IbanPersistanceDB ibanPersistanceDB) {
        return new PayOutPresenter(config, view, api, ibanAPI, moneyHelper, balanceHelper, feeHelper, ibanPersistanceDB);
    }
}
