package com.fintechplatform.ui.transactions.ui.detail.di

import com.fintechplatform.ui.email.SendEmailHelperModule
import com.fintechplatform.ui.transactions.ui.detail.TransactionDetailFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TransactionDetailPresenterModule::class, SendEmailHelperModule::class])
interface TransactionDetailViewComponent {
    fun inject(fragment: TransactionDetailFragment)
}
