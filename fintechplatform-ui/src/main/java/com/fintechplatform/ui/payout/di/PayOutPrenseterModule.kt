package com.fintechplatform.ui.payout.di

import com.fintechplatform.api.cashout.api.CashOutAPI
import com.fintechplatform.api.iban.api.IbanAPI
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper
import com.fintechplatform.ui.iban.db.IbanPersistanceDB
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.FeeHelper
import com.fintechplatform.ui.money.MoneyHelper
import com.fintechplatform.ui.payout.PayOutContract
import com.fintechplatform.ui.payout.PayOutPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PayOutPresenterModule(private val view: PayOutContract.View, private val config: DataAccount) {

    @Provides
    @Singleton
    internal fun providePayOutPresenter(api: CashOutAPI,
                                        ibanAPI: IbanAPI,
                                        moneyHelper: MoneyHelper,
                                        balanceHelper: BalanceHelper,
                                        feeHelper: FeeHelper,
                                        ibanPersistanceDB: IbanPersistanceDB): PayOutContract.Presenter = 
            PayOutPresenter(config, view, api, ibanAPI, moneyHelper, balanceHelper, feeHelper, ibanPersistanceDB)
    
}
