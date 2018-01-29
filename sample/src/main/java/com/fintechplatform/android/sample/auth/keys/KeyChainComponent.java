package com.fintechplatform.android.sample.auth.keys;


import javax.inject.Singleton;

import dagger.Component;

@Component (modules = KeyChainModule.class)
@Singleton
public interface KeyChainComponent {
    KeyChain getKeyChain();
}
