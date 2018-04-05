package com.fintechplatform.ui.transactions.ui.itemview;

import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.money.MoneyHelperModule;
import com.fintechplatform.ui.transactions.ui.TransactionItemViewHolder;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TransactionItemPresenterModule.class,
        AlertHelpersModule.class,
        MoneyHelperModule.class})
public interface TransactionItemComponent {

    void inject(TransactionItemViewHolder viewHolder);
}
