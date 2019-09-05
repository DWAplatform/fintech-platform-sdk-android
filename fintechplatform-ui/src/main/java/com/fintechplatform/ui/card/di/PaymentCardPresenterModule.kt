package com.fintechplatform.ui.card.di

import com.fintechplatform.api.card.api.PaymentCardAPI
import com.fintechplatform.ui.card.PaymentCardContract
import com.fintechplatform.ui.card.PaymentCardPresenter
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB
import com.fintechplatform.ui.models.DataAccount
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PaymentCardPresenterModule(private val view: PaymentCardContract.View, private val dataAccount: DataAccount) {

    @Provides
    @Singleton
    internal fun providesPaymentCardPresenter(api: PaymentCardAPI, paymentCardPersistenceDB: PaymentCardPersistenceDB): PaymentCardContract.Presenter =
        PaymentCardPresenter(view, api, dataAccount, paymentCardPersistenceDB)

}