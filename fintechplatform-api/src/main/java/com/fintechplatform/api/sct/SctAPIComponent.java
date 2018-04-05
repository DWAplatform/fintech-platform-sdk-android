package com.fintechplatform.api.sct;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.sct.api.SctAPI;
import com.fintechplatform.api.sct.api.SctAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 22/02/18.
 */
@Singleton
@Component (modules = {
        SctAPIModule.class,
        NetModule.class,
        LogModule.class
})
public interface SctAPIComponent {
    SctAPI getSctAPI();
}
