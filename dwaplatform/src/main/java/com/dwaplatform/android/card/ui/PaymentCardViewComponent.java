package com.dwaplatform.android.card.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.api.CardRestApiModule;
import com.dwaplatform.android.card.db.PaymentCardPersistanceModule;
import com.dwaplatform.android.card.helpers.PaymentCardHelperModule;
import com.dwaplatform.android.log.LogModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 14/12/17.
 */
@Singleton
@Component (modules = { PaymentCardPresenterModule.class,
        AlertHelpersModule.class,
        CardRestApiModule.class,
        LogModule.class,
        PaymentCardHelperModule.class,
        NetModule.class,
        PaymentCardPersistanceModule.class})
public interface PaymentCardViewComponent {
    void inject(PaymentCardActivity activity);
}
