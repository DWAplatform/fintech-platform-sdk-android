package com.fintechplatform.ui.payin

import com.fintechplatform.api.card.api.PaymentCardAPI
import com.fintechplatform.api.payin.api.PayInAPI
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.FeeHelper
import com.fintechplatform.ui.money.MoneyHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PayInPresenterModule(private val view: PayInContract.View,
                           private val configuration: DataAccount) {

    @Provides
    @Singleton
    internal fun providePayInPresenter(api: PayInAPI,
                                        apiCard: PaymentCardAPI,
                                        moneyHelper: MoneyHelper,
                                        balanceHelper: BalanceHelper,
                                        feeHelper: FeeHelper,
                                        persistenceDB: PaymentCardPersistenceDB): PayInContract.Presenter =
            PayInPresenter(configuration, view, api, apiCard, moneyHelper, balanceHelper, feeHelper, persistenceDB)
}