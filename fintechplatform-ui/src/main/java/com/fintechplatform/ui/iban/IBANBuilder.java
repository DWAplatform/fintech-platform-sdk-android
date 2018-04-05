package com.fintechplatform.ui.iban;

import com.fintechplatform.ui.iban.ui.IbanUIModule;
import com.fintechplatform.ui.models.DataAccount;


public class IBANBuilder {

    public IbanUIComponent createIBANUIComponent(String hostname, DataAccount configuration) {
        return DaggerIbanUIComponent.builder()
                .ibanUIModule(new IbanUIModule(hostname, configuration))
                .build();
    }
}
