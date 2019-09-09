package com.fintechplatform.ui.transactions.di

import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.money.MoneyHelperModule
import com.fintechplatform.ui.transactions.ui.itemview.TransactionItemViewHolder
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TransactionItemPresenterModule::class, AlertHelpersModule::class, MoneyHelperModule::class])
interface TransactionItemComponent {

    fun inject(viewHolder: TransactionItemViewHolder)
}
