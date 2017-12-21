package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.auth.keys.KeyChain;
import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.payin.models.PayInConfiguration;
import com.dwaplatform.android.secure3d.ui.Secure3DUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class PayInUIModule {

    private PayInConfiguration configuration;
    private String hostName;

    public PayInUIModule(String hostName,
                         PayInConfiguration configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }


    @Provides
    @Singleton
    PayInUI providePayInUI(Secure3DUI secure3DUI, PaymentCardUI paymentCardUI) {
        return new PayInUI(hostName, configuration, secure3DUI, paymentCardUI);
    }

}
