package com.fintechplatform.ui.account.balance.helpers

import com.fintechplatform.api.account.balance.api.BalanceAPI
import com.fintechplatform.ui.account.balance.db.DBBalance
import com.fintechplatform.ui.account.balance.db.DBBalancePersistence
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class BalanceHelperModule {

    @Provides
    @Singleton
    internal fun provideDBBalance(): DBBalance = DBBalance()

    @Provides
    @Singleton
    internal fun provideBalancePersistence(dbBalance: DBBalance): BalancePersistence = DBBalancePersistence(dbBalance)

    @Provides
    @Singleton
    internal fun provideBalanceHelper(persistence: BalancePersistence,
                                      api: BalanceAPI): BalanceHelper = BalanceHelper(persistence, api)
}
