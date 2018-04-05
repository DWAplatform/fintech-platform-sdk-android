package com.fintechplatform.ui.secure3d.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class Secure3DUIModule {

    @Provides
    @Singleton
    Secure3DUI provideSecure3D() { return new Secure3DUI(); }

}
