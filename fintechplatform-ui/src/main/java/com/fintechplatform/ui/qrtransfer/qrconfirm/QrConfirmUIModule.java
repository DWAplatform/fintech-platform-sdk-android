package com.fintechplatform.ui.qrtransfer.qrconfirm;

import com.fintechplatform.ui.card.ui.PaymentCardUI;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.payin.ui.PayInUI;
import com.fintechplatform.ui.secure3d.ui.Secure3DUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 28/02/18.
 */
@Module
public class QrConfirmUIModule {
    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;

    public QrConfirmUIModule(DataAccount configuration, String hostName, boolean isSandbox) {
        this.configuration = configuration;
        this.hostName = hostName;
        this.isSandbox = isSandbox;
    }

    @Provides
    @Singleton
    QrConfirmUI providesQrConfirmUI(PayInUI payInUI, Secure3DUI secure3DUI, PaymentCardUI paymentCardUI) {
        return new QrConfirmUI(hostName, configuration, payInUI, secure3DUI, paymentCardUI, isSandbox);
    }
}
