package com.fintechplatform.ui.cashin

import com.fintechplatform.api.card.api.PaymentCardAPI
import com.fintechplatform.api.cashin.api.CashInAPI
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.FeeHelper
import com.fintechplatform.ui.money.MoneyHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CashInPresenterModule(private val view: CashInContract.View,
                            private val configuration: DataAccount) {

    @Provides
    @Singleton
    internal fun provideCashInPresenter(api: CashInAPI,
                                        apiCard: PaymentCardAPI,
                                        moneyHelper: MoneyHelper,
                                        balanceHelper: BalanceHelper,
                                        feeHelper: FeeHelper,
                                        persistenceDB: PaymentCardPersistenceDB): CashInContract.Presenter =
            CashInPresenter(configuration, view, api, apiCard, moneyHelper, balanceHelper, feeHelper, persistenceDB)
}