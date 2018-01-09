package com.dwaplatform.android.payout.ui;

import com.dwaplatform.android.iban.ui.IbanUI;
import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PayOutUIModule {

    private String hostName;
    private DataAccount configuration;

    public PayOutUIModule(String hostName, DataAccount configuration) {
        this.configuration = configuration;
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    PayOutUI providesPayOutUI(IbanUI ibanUI) {
        return new PayOutUI(hostName, configuration, ibanUI);
    }
}
