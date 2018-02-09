package com.fintechplatform.android.transfer;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transfer.api.TransferAPIModule;
import com.fintechplatform.android.transfer.ui.TransferUIModule;

public class TransferBuilder {
//    public NetworkListUIComponent createNetworkListUIComponet(String hostName, DataAccount configuration) {
//        return DaggerNetworkListUIComponent.builder()
//                .transferUIModule(new TransferUIModule(configuration, hostName))
//                .networkUsersUIModule(new NetworkUsersUIModuleOLD(configuration, hostName))
//                .build();
//    }

    public TransferUIComponent createTrasnferUIComponent(String hostName, DataAccount configuration) {
        return DaggerTransferUIComponent.builder()
                .transferUIModule(new TransferUIModule(configuration, hostName))
                .build();
    }

    public TransferAPIComponent createTrasnferAPIComponent(Context context, String hostName) {
        return DaggerTransferAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .transferAPIModule(new TransferAPIModule(hostName))
                .build();
    }
}
