package com.fintechplatform.android.iban;

import com.fintechplatform.android.iban.ui.IbanUI;
import com.fintechplatform.android.iban.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        IbanUIModule.class
})
public interface IbanUIComponent {
    IbanUI getIbanUI();
}
