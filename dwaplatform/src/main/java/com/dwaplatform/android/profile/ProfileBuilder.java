package com.dwaplatform.android.profile;

import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.lightdata.DaggerLightDataUIComponent;
import com.dwaplatform.android.profile.lightdata.LightDataUIComponent;
import com.dwaplatform.android.profile.lightdata.ui.LightDataUIModule;

public class ProfileBuilder {

    public LightDataUIComponent createLightDataUI(String hostname, DataAccount dataAccount ) {
        return DaggerLightDataUIComponent.builder()
                .lightDataUIModule(new LightDataUIModule(hostname, dataAccount))
                .build();
    }
}
