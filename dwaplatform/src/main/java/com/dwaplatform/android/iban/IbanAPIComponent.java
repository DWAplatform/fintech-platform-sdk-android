package com.dwaplatform.android.iban;

import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.iban.api.IbanAPI;
import com.dwaplatform.android.iban.api.IbanAPIModule;
import com.dwaplatform.android.log.LogModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        IbanAPIModule.class,
        NetModule.class,
        LogModule.class
})
public interface IbanAPIComponent {
    IbanAPI getIbanAPI();
}
