package com.fintechplatform.ui.transfer.ui;

import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransferUIModule {

    private DataAccount dataAccount;
    private String hostName;

    public TransferUIModule(DataAccount dataAccount, String hostName) {
        this.dataAccount = dataAccount;
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    TransferUI providesTransferUI(){
        return new TransferUI(dataAccount, hostName);
    }
}
