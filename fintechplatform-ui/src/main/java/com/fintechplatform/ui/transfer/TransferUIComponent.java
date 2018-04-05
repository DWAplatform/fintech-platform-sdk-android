package com.fintechplatform.ui.transfer;

import com.fintechplatform.ui.transfer.ui.TransferUI;
import com.fintechplatform.ui.transfer.ui.TransferUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        TransferUIModule.class
})
public interface TransferUIComponent {
    TransferUI getTransferUI();
}
