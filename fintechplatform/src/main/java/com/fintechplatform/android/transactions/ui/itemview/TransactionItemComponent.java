package com.fintechplatform.android.transactions.ui.itemview;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.money.MoneyHelperModule;
import com.fintechplatform.android.transactions.ui.TransactionItemViewHolder;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TransactionItemPresenterModule.class,
        AlertHelpersModule.class,
        MoneyHelperModule.class})
public interface TransactionItemComponent {

    void inject(TransactionItemViewHolder viewHolder);
}
