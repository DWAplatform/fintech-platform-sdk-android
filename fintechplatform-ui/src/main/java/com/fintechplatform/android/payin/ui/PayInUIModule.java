package com.fintechplatform.android.payin.ui;

import com.fintechplatform.android.card.ui.PaymentCardUI;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.secure3d.ui.Secure3DUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PayInUIModule {

    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;

    public PayInUIModule(String hostName,
                         DataAccount configuration,
                         boolean isSandbox) {
        this.hostName = hostName;
        this.configuration = configuration;
        this.isSandbox = isSandbox;
    }


    @Provides
    @Singleton
    PayInUI providePayInUI(Secure3DUI secure3DUI, PaymentCardUI paymentCardUI) {
        return new PayInUI(hostName, configuration, secure3DUI, paymentCardUI, isSandbox);
    }

}
