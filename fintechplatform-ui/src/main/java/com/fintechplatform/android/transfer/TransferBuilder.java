package com.fintechplatform.android.transfer;

import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transfer.ui.TransferUIModule;

public class TransferBuilder {

    public TransferUIComponent createTrasnferUIComponent(String hostName, DataAccount configuration) {
        return DaggerTransferUIComponent.builder()
                .transferUIModule(new TransferUIModule(configuration, hostName))
                .build();
    }

}
