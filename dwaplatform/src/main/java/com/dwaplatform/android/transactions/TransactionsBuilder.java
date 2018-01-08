package com.dwaplatform.android.transactions;

import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.transactions.ui.TransactionsUIModule;

public class TransactionsBuilder {
    public TransactionsUIComponent createTransactionsUIComponent(String hostname, DataAccount configuration) {
        return DaggerTransactionsUIComponent.builder()
                .transactionsUIModule(new TransactionsUIModule(hostname, configuration))
                .build();
    }
}
