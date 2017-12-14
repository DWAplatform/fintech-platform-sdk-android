package com.dwaplatform.android.card.ui;

import com.dwaplatform.android.card.CardAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 13/12/17.
 */
@Module
public class PaymentCardPresenterModule {
    private PaymentCardContract.View view;

    public PaymentCardPresenterModule(PaymentCardContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    PaymentCardContract.Presenter providesPaymentCardPresenter(CardAPI api) {
        return new PaymentCardPresenter(view, api);
    }
}
