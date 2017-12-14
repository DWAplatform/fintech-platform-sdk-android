package com.dwaplatform.android.card;

import com.dwaplatform.android.card.api.CardRestAPI;
import com.dwaplatform.android.card.helpers.CardHelper;
import com.dwaplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 14/12/17.
 */

@Module
public class CardApiModule {

    @Provides
    @Singleton
    CardAPI providesCardAPI(CardRestAPI cardrestapi, Log log, CardHelper cardhelper) {
        return new CardAPI(cardrestapi, log, cardhelper);
    }
}
