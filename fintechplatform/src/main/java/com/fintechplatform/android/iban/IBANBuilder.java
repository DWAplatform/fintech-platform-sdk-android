package com.fintechplatform.android.iban;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.iban.api.IbanAPIModule;
import com.fintechplatform.android.iban.ui.IbanUIModule;
import com.fintechplatform.android.models.DataAccount;


public class IBANBuilder {
    public IbanAPIComponent createIbanAPIComponent(Context context, String hostName) {
        return DaggerIbanAPIComponent.builder()
                .ibanAPIModule(new IbanAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

    public IbanUIComponent createIBANUIComponent(String hostname, DataAccount configuration) {
        return DaggerIbanUIComponent.builder()
                .ibanUIModule(new IbanUIModule(hostname, configuration))
                .build();
    }
}
