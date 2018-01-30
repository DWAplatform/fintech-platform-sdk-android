package com.fintechplatform.android.payin.ui;

import com.fintechplatform.android.secure3d.ui.Secure3DUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class Secure3DUIModule {
    private Secure3DUI secure3DUI;

    public Secure3DUIModule(Secure3DUI secure3DUI) {
        this.secure3DUI = secure3DUI;
    }

    @Provides
    @Singleton
    Secure3DUI provideSecure3DUI() {
        return secure3DUI;
    }

}
