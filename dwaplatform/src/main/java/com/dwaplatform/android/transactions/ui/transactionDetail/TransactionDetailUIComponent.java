package com.dwaplatform.android.transactions.ui.transactionDetail;

import com.dwaplatform.android.transactions.ui.transactionDetail.ui.TransactionDetailUI;
import com.dwaplatform.android.transactions.ui.transactionDetail.ui.TransactionDetailUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = { TransactionDetailUIModule.class})
interface TransactionDetailUIComponent {
    TransactionDetailUI getTransactionDetailUI();
}
