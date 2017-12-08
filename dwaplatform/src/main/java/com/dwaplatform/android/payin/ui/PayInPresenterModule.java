package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.account.balance.helpers.BalanceHelper;
import com.dwaplatform.android.models.FeeHelper;
import com.dwaplatform.android.money.MoneyHelper;
import com.dwaplatform.android.payin.PayInContract;
import com.dwaplatform.android.payin.PayInPresenter;
import com.dwaplatform.android.payin.models.PayInConfiguration;
import com.dwaplatform.android.payin.api.PayInAPI;

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
                                                  FeeHelper feeHelper) {
        return new PayInPresenter(configuration, view, api, moneyHelper, balanceHelper, feeHelper);
    }
}
