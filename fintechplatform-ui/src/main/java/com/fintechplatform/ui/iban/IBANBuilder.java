package com.fintechplatform.ui.iban;

public class IBANBuilder {

    public IbanUIComponent createIBANUI() {
        return DaggerIbanUIComponent.builder()
                .build();
    }
}
