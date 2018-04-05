package com.fintechplatform.ui.card.ui;

import com.fintechplatform.api.card.api.PaymentCardAPI;
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB;
import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
