package com.dwaplatform.android.transactions.ui;


import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.transactions.api.TransactionsAPIModule;
import com.dwaplatform.android.transactions.ui.transactionItemView.DaggerTransactionItemComponent;
import com.dwaplatform.android.transactions.ui.transactionItemView.TransactionItemComponent;
import com.dwaplatform.android.transactions.ui.transactionItemView.TransactionItemPresenterModule;

public class TransactionsUI {

    private String hostname;
    private DataAccount configuration;

    protected static TransactionsUI instance;

    public TransactionsUI(String hostname, DataAccount configuration) {
        this.hostname = hostname;
        this.configuration = configuration;
    }

    public TransactionsViewComponent createTransactionsViewComponent(Context context, TransactionsContract.View view){
        return instance.buildTransactionsViewComponent(context, view);
    }

    protected TransactionsViewComponent buildTransactionsViewComponent(Context context, TransactionsContract.View view) {
        return DaggerTransactionsViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .keyChainModule(new KeyChainModule(context))
                .transactionsPresenterModule(new TransactionsPresenterModule(view, instance.configuration))
                .transactionsAPIModule(new TransactionsAPIModule(instance.hostname))
                .build();
    }

    protected TransactionItemComponent createTransactionItemComponent(TransactionItemViewHolder v){
        return DaggerTransactionItemComponent.builder()
                .transactionItemPresenterModule(new TransactionItemPresenterModule(v))
                .build();
    }

    public TransactionItemComponent buildTransactionItemComponent(TransactionItemViewHolder v){
        return createTransactionItemComponent(v);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, TransactionsActivity.class);
        context.startActivity(intent);
    }
}
