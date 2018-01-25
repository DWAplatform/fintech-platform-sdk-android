package com.fintechplatform.android.card.ui;

import com.fintechplatform.android.card.api.PaymentCardAPI;
import com.fintechplatform.android.card.db.PaymentCardPersistenceDB;
import com.fintechplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 13/12/17.
 */
@Module
public class PaymentCardPresenterModule {
    private PaymentCardContract.View view;
    private DataAccount dataAccount;

    public PaymentCardPresenterModule(PaymentCardContract.View view, DataAccount dataAccount) {
        this.view = view;
        this.dataAccount = dataAccount;
    }

    @Provides
    @Singleton
    PaymentCardContract.Presenter providesPaymentCardPresenter(PaymentCardAPI api, PaymentCardPersistenceDB paymentCardPersistenceDB) {
        return new PaymentCardPresenter(view, api, dataAccount, paymentCardPersistenceDB);
    }

}
