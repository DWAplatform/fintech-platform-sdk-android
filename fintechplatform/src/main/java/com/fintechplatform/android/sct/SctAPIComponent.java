package com.fintechplatform.android.sct;

import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.sct.api.SctAPI;
import com.fintechplatform.android.sct.api.SctAPIModule;

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
