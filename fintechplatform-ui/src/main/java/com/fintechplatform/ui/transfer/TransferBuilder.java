package com.fintechplatform.ui.transfer;

import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.transfer.ui.TransferUIModule;

public class TransferBuilder {

    public TransferUIComponent createTrasnferUIComponent(String hostName, DataAccount configuration) {
        return DaggerTransferUIComponent.builder()
                .transferUIModule(new TransferUIModule(configuration, hostName))
                .build();
    }

}
