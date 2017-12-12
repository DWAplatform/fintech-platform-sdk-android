package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.payin.models.PayInConfiguration;
import com.dwaplatform.android.secure3d.ui.Secure3DUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class PayInUIModule {

    private PayInConfiguration configuration;
    private String hostName;
    private String token;
    private Secure3DUI secure3DUI;


    public PayInUIModule(String hostName,
                         String token,
                         PayInConfiguration configuration,
                         Secure3DUI secure3DUI) {
        this.hostName = hostName;
        this.token = token;
        this.configuration = configuration;
        this.secure3DUI = secure3DUI;
    }

    @Provides
    @Singleton
    PayInUI providePayInUI() {
        return new PayInUI(hostName, token, configuration, secure3DUI);
    }

}
