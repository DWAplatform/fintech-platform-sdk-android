package com.fintechplatform.api.iban;

import com.fintechplatform.api.iban.api.IbanAPI;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;

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
