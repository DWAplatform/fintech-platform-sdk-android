package com.fintechplatform.ui.sct.ui;

import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 22/02/18.
 */
@Module
public class SctUIModule {
    private DataAccount dataAccount;
    private String hostName;

    public SctUIModule(DataAccount dataAccount, String hostName) {
        this.dataAccount = dataAccount;
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    SctUI providesSctUI(){
        return new SctUI(dataAccount, hostName);
    }
}
