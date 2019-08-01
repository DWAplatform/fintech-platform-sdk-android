package com.fintechplatform.api.transfer;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.transfer.api.TransferAPIModule;

public class TransferAPIBuilder {

    public com.fintechplatform.api.transfer.TransferAPIComponent createTrasnferAPIComponent(Context context, String hostName) {
        return DaggerTransferAPIComponent.builder()
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), hostName)))
                .transferAPIModule(new TransferAPIModule(hostName))
                .build();
    }
}