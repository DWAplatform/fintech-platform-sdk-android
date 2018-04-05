package com.fintechplatform.android.iban;

import com.fintechplatform.android.iban.ui.IbanUIModule;
import com.fintechplatform.android.models.DataAccount;


public class IBANBuilder {

    public IbanUIComponent createIBANUIComponent(String hostname, DataAccount configuration) {
        return DaggerIbanUIComponent.builder()
                .ibanUIModule(new IbanUIModule(hostname, configuration))
                .build();
    }
}
