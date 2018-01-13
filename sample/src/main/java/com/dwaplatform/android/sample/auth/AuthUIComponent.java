package com.dwaplatform.android.sample.auth;

import com.dwaplatform.android.sample.auth.ui.AuthUI;
import com.dwaplatform.android.sample.auth.ui.AuthUIModule;

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
    AuthUI getAuthUI();
}
