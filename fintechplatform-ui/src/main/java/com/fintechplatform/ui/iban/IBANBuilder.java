package com.fintechplatform.ui.iban;

import com.fintechplatform.ui.iban.ui.IbanUI;


public class IBANBuilder {

    public IbanUI createIBANUI() {
        return DaggerIbanUIComponent.builder()
                .build().getIbanUI();
    }
}
