package com.fintechplatform.android.sample.auth.keys;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 19/12/17.
 */
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
