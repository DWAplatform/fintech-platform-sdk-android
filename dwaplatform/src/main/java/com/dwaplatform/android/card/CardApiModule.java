package com.dwaplatform.android.card;

import com.dwaplatform.android.card.api.CardRestAPI;
import com.dwaplatform.android.card.helpers.PaymentCardHelper;
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
    CardAPI providesCardAPI(CardRestAPI cardrestapi, Log log, PaymentCardHelper cardhelper) {
        return new CardAPI(cardrestapi, log, cardhelper);
    }
}
