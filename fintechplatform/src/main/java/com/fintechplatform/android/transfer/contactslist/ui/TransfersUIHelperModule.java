package com.fintechplatform.android.transfer.contactslist.ui;

import com.fintechplatform.android.transfer.ui.TransferUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransfersUIHelperModule {

    private TransferUI transferUI;

    public TransfersUIHelperModule(TransferUI transferUI) {
        this.transferUI = transferUI;
    }

    @Provides
    @Singleton
    TransferUI providesTransferUI() {
        return transferUI;
    }
}
