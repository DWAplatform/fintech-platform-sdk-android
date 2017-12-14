package com.dwaplatform.android.card.helpers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 14/12/17.
 */

@Module
public class CardHelperModule {

    @Singleton
    @Provides
    CardHelper providesCardHelper(SanityCheck sanityCheck) {
        return new CardHelper(sanityCheck);
    }

    @Singleton
    @Provides
    SanityCheck providesSanityCheck() {
        return new SanityCheck();
    }

}
