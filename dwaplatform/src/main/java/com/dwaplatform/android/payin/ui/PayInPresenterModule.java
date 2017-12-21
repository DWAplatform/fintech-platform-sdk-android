package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.account.balance.helpers.BalanceHelper;
import com.dwaplatform.android.auth.keys.KeyChain;
import com.dwaplatform.android.money.FeeHelper;
import com.dwaplatform.android.money.MoneyHelper;
import com.dwaplatform.android.payin.PayInContract;
import com.dwaplatform.android.payin.PayInPresenter;
import com.dwaplatform.android.payin.api.PayInAPI;
import com.dwaplatform.android.payin.models.PayInConfiguration;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class PayInPresenterModule {

    private final PayInContract.View view;
    private final PayInConfiguration configuration;

    public PayInPresenterModule(PayInContract.View view,
                                PayInConfiguration configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    PayInContract.Presenter providePayInPresenter(PayInAPI api,
                                                  MoneyHelper moneyHelper,
                                                  BalanceHelper balanceHelper,
                                                  FeeHelper feeHelper,
                                                  KeyChain key) {
        return new PayInPresenter(configuration, view, api, moneyHelper, balanceHelper, feeHelper, key);
    }
}
