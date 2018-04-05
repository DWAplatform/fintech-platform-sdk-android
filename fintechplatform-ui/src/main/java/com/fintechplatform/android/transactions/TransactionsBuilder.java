package com.fintechplatform.android.transactions;

import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transactions.ui.TransactionsUIModule;

public class TransactionsBuilder {
    public TransactionsUIComponent createTransactionsUI(String hostname, DataAccount configuration) {
        return DaggerTransactionsUIComponent.builder()
                .transactionsUIModule(new TransactionsUIModule(hostname, configuration))
                .build();
    }

}
