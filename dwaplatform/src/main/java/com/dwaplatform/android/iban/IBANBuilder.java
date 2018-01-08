package com.dwaplatform.android.iban;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.iban.ui.IbanUIModule;
import com.dwaplatform.android.models.DataAccount;


public class IBANBuilder {
    public IbanAPIComponent createIbanAPIComponent(Context context) {
        return DaggerIbanAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .build();
    }

    public IbanUIComponent createIBANUIComponent(String hostname, DataAccount configuration) {
        return DaggerIbanUIComponent.builder()
                .ibanUIModule(new IbanUIModule(hostname, configuration))
                .build();
    }
}
