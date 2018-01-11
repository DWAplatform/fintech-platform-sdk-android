package com.dwaplatform.android.transactions.ui.itemview;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.money.MoneyHelperModule;
import com.dwaplatform.android.transactions.ui.TransactionItemViewHolder;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TransactionItemPresenterModule.class,
        AlertHelpersModule.class,
        MoneyHelperModule.class})
public interface TransactionItemComponent {

    void inject(TransactionItemViewHolder viewHolder);
}
