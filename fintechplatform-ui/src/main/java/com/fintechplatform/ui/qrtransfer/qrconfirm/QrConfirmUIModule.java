package com.fintechplatform.ui.qrtransfer.qrconfirm;

import com.fintechplatform.ui.models.DataAccount;

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
    QrConfirmUI providesQrConfirmUI() {
        return new QrConfirmUI(hostName, configuration, isSandbox);
    }
}
