package com.fintechplatform.android.transfer;

import com.fintechplatform.android.transfer.ui.TransferUI;
import com.fintechplatform.android.transfer.ui.TransferUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        TransferUIModule.class
})
public interface TransferUIComponent {
    TransferUI getTransferUI();
}
