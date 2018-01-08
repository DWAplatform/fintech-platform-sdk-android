package com.dwaplatform.android.transactions.ui.transactionDetail;

public class TransactionDetailBuilder {
    public TransactionDetailUIComponent cerateTransactionDetailComponent() {
        return DaggerTransactionDetailUIComponent.builder()
                .build();
    }
}
