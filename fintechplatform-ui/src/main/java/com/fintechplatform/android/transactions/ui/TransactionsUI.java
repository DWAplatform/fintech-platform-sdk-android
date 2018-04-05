package com.fintechplatform.android.transactions.ui;


import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.transactions.api.TransactionsAPIModule;
import com.fintechplatform.android.transactions.ui.detail.ui.TransactionDetailUI;
import com.fintechplatform.android.transactions.ui.itemview.DaggerTransactionItemComponent;
import com.fintechplatform.android.transactions.ui.itemview.TransactionItemComponent;
import com.fintechplatform.android.transactions.ui.itemview.TransactionItemPresenterModule;

public class TransactionsUI {

    private String hostname;
    private DataAccount configuration;
    private TransactionDetailUI detailUI;

    protected static TransactionsUI instance;

    public TransactionsUI(String hostname, DataAccount configuration, TransactionDetailUI detailUI) {
        this.hostname = hostname;
        this.configuration = configuration;
        this.detailUI = detailUI;
    }

    public TransactionsViewComponent createTransactionsViewComponent(Context context, TransactionsContract.View view){
        return instance.buildTransactionsViewComponent(context, view);
    }

    protected TransactionsViewComponent buildTransactionsViewComponent(Context context, TransactionsContract.View view) {
        return DaggerTransactionsViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostname))
                .transactionsPresenterModule(new TransactionsPresenterModule(view, instance.configuration))
                .transactionsAPIModule(new TransactionsAPIModule(instance.hostname))
                .transactionDetailUIModule(new TransactionDetailUIModule(detailUI))
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
