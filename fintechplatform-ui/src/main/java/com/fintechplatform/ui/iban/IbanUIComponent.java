package com.fintechplatform.ui.iban;

import com.fintechplatform.ui.iban.ui.IbanUI;
import com.fintechplatform.ui.iban.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        IbanUIModule.class
})
public interface IbanUIComponent {
    IbanUI getIbanUI();
}
