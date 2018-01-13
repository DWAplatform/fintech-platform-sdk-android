package com.dwaplatform.android.payout.ui;


import com.dwaplatform.android.account.balance.helpers.BalanceHelper;
import com.dwaplatform.android.iban.db.IbanPersistanceDB;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.money.FeeHelper;
import com.dwaplatform.android.money.MoneyHelper;
import com.dwaplatform.android.payout.api.PayOutAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 11/09/17.
 */
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
                                                    MoneyHelper moneyHelper,
                                                    BalanceHelper balanceHelper,
                                                    FeeHelper feeHelper,
                                                    IbanPersistanceDB ibanPersistanceDB) {
        return new PayOutPresenter(config, view, api, moneyHelper, balanceHelper, feeHelper, ibanPersistanceDB);
    }
}
