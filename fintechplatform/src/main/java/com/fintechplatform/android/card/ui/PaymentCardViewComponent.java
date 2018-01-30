package com.fintechplatform.android.card.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.card.api.PaymentCardAPIModule;
import com.fintechplatform.android.card.db.PaymentCardPersistanceModule;
import com.fintechplatform.android.card.helpers.PaymentCardHelperModule;
import com.fintechplatform.android.log.LogModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = { PaymentCardPresenterModule.class,
        AlertHelpersModule.class,
        PaymentCardAPIModule.class,
        LogModule.class,
        PaymentCardHelperModule.class,
        NetModule.class,
        PaymentCardPersistanceModule.class})
public interface PaymentCardViewComponent {
    void inject(PaymentCardActivity activity);
}
