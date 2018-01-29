package com.fintechplatform.android.sample.auth.keys;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class KeyChainModule {

    private Context context;

    public KeyChainModule(Context context){
        this.context = context;
    }
    @Provides
    @Singleton
    KeyChain providesKeyChain() {
        return new KeyChain(context);
    }
}
