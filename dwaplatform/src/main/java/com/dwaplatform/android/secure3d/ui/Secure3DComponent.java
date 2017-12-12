package com.dwaplatform.android.secure3d.ui;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 12/12/17.
 */
@Singleton
@Component(modules = Secure3DPresenterModule.class)
public interface Secure3DComponent {
    void inject(Secure3DActivity activity);
}