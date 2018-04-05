package com.fintechplatform.android.payout;

import com.fintechplatform.android.iban.ui.IbanUIModule;
import com.fintechplatform.android.payout.ui.PayOutUI;
import com.fintechplatform.android.payout.ui.PayOutUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        PayOutUIModule.class,
        IbanUIModule.class
})
public interface PayOutUIComponent {
    PayOutUI getPayOutUI();
}
