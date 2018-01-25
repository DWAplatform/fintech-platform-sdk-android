package com.dwaplatform.android.iban;

import com.dwaplatform.android.iban.ui.IbanUI;
import com.dwaplatform.android.iban.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        IbanUIModule.class
})
public interface IbanUIComponent {
    IbanUI getIbanUI();
}
