package com.dwaplatform.android.card.ui;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 13/12/17.
 */
@Singleton
@Component (modules = {})
public interface PaymentCardViewComponent {
    void inject(PaymentCardActivity activity);
}
