package com.fintechplatform.api.pagopa;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.pagopa.api.PagoPaAPI;
import com.fintechplatform.api.pagopa.api.PagoPaAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 18/04/18.
 */
@Singleton
@Component (modules = {
        PagoPaAPIModule.class,
        NetModule.class,
        LogModule.class
})
public interface PagoPaAPIComponent {
    PagoPaAPI getPagoPaAPI();
}
