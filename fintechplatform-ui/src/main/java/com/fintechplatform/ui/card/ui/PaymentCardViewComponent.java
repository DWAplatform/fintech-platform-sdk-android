package com.fintechplatform.ui.card.ui;

import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.ui.card.db.PaymentCardPersistanceModule;
import com.fintechplatform.api.card.helpers.PaymentCardHelperModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;

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
