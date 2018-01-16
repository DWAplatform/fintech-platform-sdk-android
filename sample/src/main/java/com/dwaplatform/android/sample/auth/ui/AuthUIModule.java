package com.dwaplatform.android.sample.auth.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by ingrid on 19/12/17.
 */
@Module
public class AuthUIModule {
    private String userid;
    private String hostname;
    private Function1<String, Unit> completion;

    public AuthUIModule(String userid, String hostname, Function1<String, Unit> completion) {
        this.userid = userid;
        this.hostname = hostname;
        this.completion = completion;
    }

    @Provides
    @Singleton
    AuthUI providesAuthUI(){
        return new AuthUI(hostname, userid, completion);
    }
}
