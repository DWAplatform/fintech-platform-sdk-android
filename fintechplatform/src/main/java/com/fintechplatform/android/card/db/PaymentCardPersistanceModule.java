package com.fintechplatform.android.card.db;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
