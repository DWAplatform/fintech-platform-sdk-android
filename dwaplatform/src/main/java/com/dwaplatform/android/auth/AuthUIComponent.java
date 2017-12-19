package com.dwaplatform.android.auth;

import com.dwaplatform.android.auth.ui.AuthUI;
import com.dwaplatform.android.auth.ui.AuthUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 19/12/17.
 */
@Singleton
@Component (modules = {
        AuthUIModule.class
})
public interface AuthUIComponent {
    AuthUI buildAuthUI();
}
