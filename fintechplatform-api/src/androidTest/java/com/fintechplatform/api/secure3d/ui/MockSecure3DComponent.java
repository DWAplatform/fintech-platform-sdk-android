package com.fintechplatform.api.secure3d.ui;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { MockSecure3DPresenterModule.class, Secure3DMockUI.class } )
public interface MockSecure3DComponent extends Secure3DViewComponent {
    void inject(Secure3DActivityTest activity);
}