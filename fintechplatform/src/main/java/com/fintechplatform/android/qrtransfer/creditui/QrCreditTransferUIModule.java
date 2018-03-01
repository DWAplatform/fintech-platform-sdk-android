package com.fintechplatform.android.qrtransfer.creditui;

import com.fintechplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 28/02/18.
 */
@Module
public class QrCreditTransferUIModule {

    private String hostName;
    private DataAccount dataAccount;

    public QrCreditTransferUIModule(String hostName, DataAccount dataAccount) {
        this.hostName = hostName;
        this.dataAccount = dataAccount;
    }

    @Provides
    @Singleton
    QrCreditTransferUI providesQrCreditUI(){
        return new QrCreditTransferUI(dataAccount,hostName);
    }
}
