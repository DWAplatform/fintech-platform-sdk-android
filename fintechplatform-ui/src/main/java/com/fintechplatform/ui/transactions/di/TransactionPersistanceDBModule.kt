package com.fintechplatform.ui.transactions.di

import com.fintechplatform.ui.money.MoneyHelper
import com.fintechplatform.ui.transactions.db.TransactionDB
import com.fintechplatform.ui.transactions.db.TransactionPersistanceDB
import com.fintechplatform.ui.transactions.models.TransactionDetailHelper
import com.fintechplatform.ui.transactions.models.TransactionHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TransactionPersistanceDBModule {

    @Provides
    @Singleton
    internal fun providesTransactionsPersistanceDB(db: TransactionDB, helper: TransactionHelper): TransactionPersistanceDB {
        return TransactionPersistanceDB(db, helper)
    }

    @Provides
    @Singleton
    internal fun providesTransactionDB(): TransactionDB {
        return TransactionDB()
    }

    @Provides
    @Singleton
    internal fun providesTransactionHelper(moneyHelper: MoneyHelper): TransactionHelper {
        return TransactionHelper(moneyHelper)
    }

    @Provides
    @Singleton
    internal fun providesTransactionDetailHelper(): TransactionDetailHelper {
        return TransactionDetailHelper()
    }
}
