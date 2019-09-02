package com.fintechplatform.ui.secure3d.ui;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        Secure3DPresenterModule.class }
)
public interface Secure3DViewComponent {
    void inject(Secure3DFragment fragment);
}