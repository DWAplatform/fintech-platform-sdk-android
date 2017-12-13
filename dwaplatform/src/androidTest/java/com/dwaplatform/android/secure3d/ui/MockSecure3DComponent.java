package com.dwaplatform.android.secure3d.ui;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 13/12/17.
 */
@Singleton
@Component(modules = { MockSecure3DPresenterModule.class, Secure3DMockUI.class } )
public interface MockSecure3DComponent extends Secure3DViewComponent {
    void inject(Secure3DActivityTest activity);
}