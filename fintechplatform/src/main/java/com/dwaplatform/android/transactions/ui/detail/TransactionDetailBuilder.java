package com.dwaplatform.android.transactions.ui.detail;

public class TransactionDetailBuilder {
    public TransactionDetailUIComponent cerateTransactionDetailComponent() {
        return DaggerTransactionDetailUIComponent.builder()
                .build();
    }
}
