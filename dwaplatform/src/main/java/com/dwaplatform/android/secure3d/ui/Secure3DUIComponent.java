package com.dwaplatform.android.secure3d.ui;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 12/12/17.
 */
@Singleton
@Component (modules = Secure3DUIModule.class)
public interface Secure3DUIComponent {
    Secure3DUI getSecure3DUI();
}
