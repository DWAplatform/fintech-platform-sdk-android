package com.dwaplatform.android.models;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 19/12/17.
 */
@Module
public class SendEmailHelperModule {
    @Provides
    @Singleton
    SendEmailHelper providesSendEmailHelper(SendEmailIntentHelper intentHelper){
        return new SendEmailHelper(intentHelper);
    }

    @Provides
    @Singleton
    SendEmailIntentHelper providesIntentEmailHelper(){
        return new SendEmailIntentHelper();
    }
}
