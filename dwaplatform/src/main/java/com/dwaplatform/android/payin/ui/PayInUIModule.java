package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.payin.models.PayInConfiguration;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class PayInUIModule {

    private PayInConfiguration configuration;
    private String hostName;
    private String token;


    public PayInUIModule(String hostName,
                         String token,
                         PayInConfiguration configuration) {
        this.hostName = hostName;
        this.token = token;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    PayInUI providePayInUI() {
        return new PayInUI(hostName, token, configuration);
    }

}
