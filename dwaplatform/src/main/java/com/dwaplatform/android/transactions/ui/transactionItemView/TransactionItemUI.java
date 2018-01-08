package com.dwaplatform.android.transactions.ui.transactionItemView;


import android.content.Context;

public class TransactionItemUI {
    protected static TransactionItemUI instance;

    protected TransactionItemComponent createTransactionItemComponent(TransactionItemViewHolder v){
        return DaggerTransactionItemComponent.builder()
                .transactionItemPresenterModule(new TransactionItemPresenterModule(v))
                .build();
    }

    public TransactionItemComponent buildTransactionItemComponent(TransactionItemViewHolder v){
        return instance.createTransactionItemComponent(v);
    }

    public void getInstance() {
        instance = this;
    }
}
