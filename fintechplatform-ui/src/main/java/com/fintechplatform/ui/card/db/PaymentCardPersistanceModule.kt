package com.fintechplatform.ui.card.db

import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PaymentCardPersistanceModule {

    @Provides
    @Singleton
    internal fun providesPayamentCardPersistance(paymentCardDb: PaymentCardDB): PaymentCardPersistenceDB {
        return PaymentCardPersistenceDB(paymentCardDb)
    }

    @Provides
    @Singleton
    internal fun providesPaymentCardDB(): PaymentCardDB {
        return PaymentCardDB()
    }
}