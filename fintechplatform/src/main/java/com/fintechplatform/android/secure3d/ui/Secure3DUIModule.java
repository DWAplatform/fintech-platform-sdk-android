package com.fintechplatform.android.secure3d.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 13/12/17.
 */

@Module
public class Secure3DUIModule {

    @Provides
    @Singleton
    Secure3DUI provideSecure3D() { return new Secure3DUI(); }

}
