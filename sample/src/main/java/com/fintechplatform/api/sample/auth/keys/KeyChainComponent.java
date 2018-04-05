package com.fintechplatform.api.sample.auth.keys;


import javax.inject.Singleton;

import dagger.Component;

@Component (modules = KeyChainModule.class)
@Singleton
public interface KeyChainComponent {
    KeyChain getKeyChain();
}
