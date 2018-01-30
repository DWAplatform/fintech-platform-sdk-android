package com.fintechplatform.android.secure3d;


import com.fintechplatform.android.secure3d.ui.Secure3DUI;
import com.fintechplatform.android.secure3d.ui.Secure3DUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = Secure3DUIModule.class)
public interface Secure3DUIComponent {
    Secure3DUI getSecure3DUI();
}
