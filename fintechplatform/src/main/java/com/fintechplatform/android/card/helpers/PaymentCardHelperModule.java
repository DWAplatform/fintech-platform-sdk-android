package com.fintechplatform.android.card.helpers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 14/12/17.
 */

@Module
public class PaymentCardHelperModule {

    @Singleton
    @Provides
    PaymentCardHelper providesCardHelper(SanityCheck sanityCheck) {
        return new PaymentCardHelper(sanityCheck);
    }

    @Singleton
    @Provides
    SanityCheck providesSanityCheck() {
        return new SanityCheck();
    }

}
