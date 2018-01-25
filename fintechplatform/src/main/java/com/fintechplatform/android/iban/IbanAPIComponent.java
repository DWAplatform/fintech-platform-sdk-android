package com.fintechplatform.android.iban;

import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.iban.api.IbanAPI;
import com.fintechplatform.android.iban.api.IbanAPIModule;
import com.fintechplatform.android.log.LogModule;

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
