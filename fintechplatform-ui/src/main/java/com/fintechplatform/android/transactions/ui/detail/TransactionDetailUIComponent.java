package com.fintechplatform.android.transactions.ui.detail;

import com.fintechplatform.android.transactions.ui.detail.ui.TransactionDetailUI;
import com.fintechplatform.android.transactions.ui.detail.ui.TransactionDetailUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = { TransactionDetailUIModule.class})
interface TransactionDetailUIComponent {
    TransactionDetailUI getTransactionDetailUI();
}
