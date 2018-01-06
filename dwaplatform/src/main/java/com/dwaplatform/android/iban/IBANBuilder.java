package com.dwaplatform.android.iban;

import com.dwaplatform.android.iban.ui.IbanUIModule;
import com.dwaplatform.android.models.DataAccount;


public class IBANBuilder {



    public IbanUIComponent createIBANUIComponent(String hostname, DataAccount configuration) {
        return DaggerIbanUIComponent.builder()
                .ibanUIModule(new IbanUIModule(hostname, configuration))
                .build();
    }
}
