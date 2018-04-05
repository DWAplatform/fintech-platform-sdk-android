package com.fintechplatform.android.transfer;

import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.transfer.api.TransferAPI;
import com.fintechplatform.android.transfer.api.TransferAPIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        TransferAPIModule.class,
        NetModule.class,
        LogModule.class
})
public interface TransferAPIComponent {
    TransferAPI getTransferAPI();
}
