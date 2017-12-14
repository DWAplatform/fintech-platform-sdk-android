package com.dwaplatform.android.card.db;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 14/12/17.
 */
@Module
public class PaymentCardPersistanceModule {

    @Provides
    @Singleton
    PaymentCardPersistenceDB providesPayamentCardPersistance(PaymentCardDB paymentCardDb) {
        return new PaymentCardPersistenceDB(paymentCardDb);
    }

    @Provides
    @Singleton
    PaymentCardDB providesPaymentCardDB(){
        return new PaymentCardDB();
    }
}
