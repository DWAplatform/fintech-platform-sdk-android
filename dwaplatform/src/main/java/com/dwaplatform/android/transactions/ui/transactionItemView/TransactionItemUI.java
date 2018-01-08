package com.dwaplatform.android.transactions.ui.transactionItemView;


import android.content.Context;

public class TransactionItemUI {
    protected static TransactionItemUI instance;

    protected TransactionItemComponent createTransactionItemComponent(Context context, TransactionItemViewHolder v){
        return DaggerTransactionItemComponent.builder()
                .transactionItemPresenterModule(new TransactionItemPresenterModule(v))
                .build();
    }
    public TransactionItemComponent buildTransactionItemComponent(Context context, TransactionItemViewHolder v){
        return instance.createTransactionItemComponent(context, v);
    }
}
