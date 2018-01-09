package com.dwaplatform.android.transactions.ui.transactionDetail.ui;

import android.content.Context;
import android.content.Intent;
import com.dwaplatform.android.transactions.models.TransactionItem;

public class TransactionDetailUI {
    protected static TransactionDetailUI instance;

    protected TransactionDetailViewComponent createTransactionDetailComponent(TransactionDetailContract.View view) {
        return DaggerTransactionDetailViewComponent.builder()
                .transactionDetailPresenterModule(new TransactionDetailPresenterModule(view))
                .build();
    }
    public TransactionDetailViewComponent buildTransactioDetailComponent(TransactionDetailContract.View view) {
        return instance.createTransactionDetailComponent(view);
    }

    public void start(Context context, TransactionItem item) {
        instance = this;
        Intent intent = new Intent(context, TransactionDetailActivity.class);
        intent.putExtra("transaction", item);
        context.startActivity(intent);
    }
}
