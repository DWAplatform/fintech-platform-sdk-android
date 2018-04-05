package com.fintechplatform.android.transfer;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.transfer.api.TransferAPIModule;

public class TransferBuilder {

    public TransferAPIComponent createTrasnferAPIComponent(Context context, String hostName) {
        return DaggerTransferAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .transferAPIModule(new TransferAPIModule(hostName))
                .build();
    }
}