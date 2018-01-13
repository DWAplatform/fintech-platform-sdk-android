package com.dwaplatform.android.card.ui;

import com.dwaplatform.android.card.api.PaymentCardAPI;
import com.dwaplatform.android.card.db.PaymentCardPersistenceDB;
import com.dwaplatform.android.models.DataAccount;

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
