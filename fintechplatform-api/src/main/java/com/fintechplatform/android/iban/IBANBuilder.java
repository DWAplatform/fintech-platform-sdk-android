package com.fintechplatform.android.iban;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.iban.api.IbanAPIModule;
import com.fintechplatform.android.net.NetModule;


public class IBANBuilder {
    public IbanAPIComponent createIbanAPIComponent(Context context, String hostName) {
        return DaggerIbanAPIComponent.builder()
                .ibanAPIModule(new IbanAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }
}
