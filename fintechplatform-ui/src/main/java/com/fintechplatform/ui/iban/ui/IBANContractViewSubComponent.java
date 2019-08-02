package com.fintechplatform.ui.iban.ui;

import dagger.BindsInstance;
import dagger.Subcomponent;

@Subcomponent(modules = {
        IBANFragmentModule.class
})
public interface IBANContractViewSubComponent {
    @Subcomponent.Builder
    interface Builder {
        IBANContractViewSubComponent build();

        @BindsInstance
        Builder fragment(IBANFragment fragment);

    }

    void inject(IBANFragment fragment);
}
