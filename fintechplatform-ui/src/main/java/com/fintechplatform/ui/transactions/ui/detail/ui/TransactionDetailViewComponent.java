package com.fintechplatform.ui.transactions.ui.detail.ui;

import com.fintechplatform.ui.email.SendEmailHelperModule;

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
