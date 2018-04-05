package com.fintechplatform.api.transactions;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.transactions.api.TransactionsAPIModule;

public class TransactionAPIBuilder {

    public TransactionsAPIComponent createTransactionsAPI(Context context, String hostname) {
        return DaggerTransactionsAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostname))
                .transactionsAPIModule(new TransactionsAPIModule(hostname))
                .build();
    }
}
