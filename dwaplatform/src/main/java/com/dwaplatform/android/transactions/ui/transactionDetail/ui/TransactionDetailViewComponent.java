package com.dwaplatform.android.transactions.ui.transactionDetail.ui;

import com.dwaplatform.android.email.SendEmailHelperModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        TransactionDetailPresenterModule.class,
        TransactionDetailUIModule.class,
        SendEmailHelperModule.class
})
public interface TransactionDetailViewComponent {
    void inject(TransactionDetailActivity activity);
}
