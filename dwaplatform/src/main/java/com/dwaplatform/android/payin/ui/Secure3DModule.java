package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.secure3d.ui.Secure3DUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 12/12/17.
 */
@Module
public class Secure3DModule {
    private Secure3DUI secure3DUI;

    public Secure3DModule(Secure3DUI secure3DUI) {
        this.secure3DUI = secure3DUI;
    }

    @Provides
    @Singleton
    Secure3DUI provideSecure3DUI() {
        return secure3DUI;
    }

}
