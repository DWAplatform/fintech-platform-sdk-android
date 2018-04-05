package com.fintechplatform.api.sample.auth;

import com.fintechplatform.api.sample.auth.ui.AuthUI;
import com.fintechplatform.api.sample.auth.ui.AuthUIModule;

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
