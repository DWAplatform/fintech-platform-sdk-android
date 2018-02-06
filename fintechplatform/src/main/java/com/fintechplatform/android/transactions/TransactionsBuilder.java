package com.fintechplatform.android.transactions;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transactions.api.TransactionsAPIModule;
import com.fintechplatform.android.transactions.ui.TransactionsUIModule;

public class TransactionsBuilder {
    public TransactionsUIComponent createTransactionsUI(String hostname, DataAccount configuration) {
        return DaggerTransactionsUIComponent.builder()
                .transactionsUIModule(new TransactionsUIModule(hostname, configuration))
                .build();
    }

    public TransactionsAPIComponent createTransactionsAPI(Context context, String hostname) {
        return DaggerTransactionsAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostname))
                .transactionsAPIModule(new TransactionsAPIModule(hostname))
                .build();
    }
}
