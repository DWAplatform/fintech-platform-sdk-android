package com.fintechplatform.api.iban;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;


public class IBANAPIBuilder {
    public IbanAPIComponent createIbanAPIComponent(Context context, String hostName) {
        return DaggerIbanAPIComponent.builder()
                .ibanAPIModule(new IbanAPIModule(hostName))
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), hostName)))
                .build();
    }
}
