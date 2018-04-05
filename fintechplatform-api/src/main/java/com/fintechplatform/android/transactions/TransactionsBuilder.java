package com.fintechplatform.android.transactions;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.transactions.api.TransactionsAPIModule;

public class TransactionsBuilder {

    public TransactionsAPIComponent createTransactionsAPI(Context context, String hostname) {
        return DaggerTransactionsAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostname))
                .transactionsAPIModule(new TransactionsAPIModule(hostname))
                .build();
    }
}
