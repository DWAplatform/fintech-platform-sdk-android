package com.dwaplatform.android.transactions.ui.transactionItemView;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.money.MoneyHelperModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TransactionItemPresenterModule.class,
        AlertHelpersModule.class,
        MoneyHelperModule.class})
public interface TransactionItemComponent {

    void inject(TransactionItemViewHolder viewHolder);
}
