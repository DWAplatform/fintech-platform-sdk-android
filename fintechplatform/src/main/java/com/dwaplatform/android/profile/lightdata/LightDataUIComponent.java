package com.dwaplatform.android.profile.lightdata;

import com.dwaplatform.android.profile.lightdata.ui.LightDataUI;
import com.dwaplatform.android.profile.lightdata.ui.LightDataUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        LightDataUIModule.class
})
public interface LightDataUIComponent {
    LightDataUI getLightData();
}
