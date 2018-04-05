package com.fintechplatform.api.transfer;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.transfer.api.TransferAPI;
import com.fintechplatform.api.transfer.api.TransferAPIModule;

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
