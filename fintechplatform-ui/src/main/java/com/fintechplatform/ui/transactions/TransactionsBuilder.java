package com.fintechplatform.ui.transactions;

import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.transactions.ui.TransactionsUIModule;

public class TransactionsBuilder {
    public TransactionsUIComponent createTransactionsUI(String hostname, DataAccount configuration) {
        return DaggerTransactionsUIComponent.builder()
                .transactionsUIModule(new TransactionsUIModule(hostname, configuration))
                .build();
    }

    public TransactionsHelperComponent createTransactionHelper() {
        return DaggerTransactionsHelperComponent.builder().build();
    }
}
