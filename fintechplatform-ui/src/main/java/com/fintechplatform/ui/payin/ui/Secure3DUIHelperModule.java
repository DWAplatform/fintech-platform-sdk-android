package com.fintechplatform.ui.payin.ui;

import com.fintechplatform.ui.secure3d.ui.Secure3DUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class Secure3DUIHelperModule {
    private Secure3DUI secure3DUI;

    public Secure3DUIHelperModule(Secure3DUI secure3DUI) {
        this.secure3DUI = secure3DUI;
    }

    @Provides
    @Singleton
    Secure3DUI provideSecure3DUI() {
        return secure3DUI;
    }

}
