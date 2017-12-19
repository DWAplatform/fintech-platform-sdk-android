package com.dwaplatform.android.keys;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 19/12/17.
 */
@Component (modules = KeyChainModule.class)
@Singleton
public interface KeyChainComponent {
    KeyChain buildKeyChain();
}
