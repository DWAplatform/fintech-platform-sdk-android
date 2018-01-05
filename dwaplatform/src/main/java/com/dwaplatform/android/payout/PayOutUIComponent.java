package com.dwaplatform.android.payout;

import com.dwaplatform.android.iban.ui.IbanUIModule;
import com.dwaplatform.android.payout.ui.PayOutUI;
import com.dwaplatform.android.payout.ui.PayOutUIModule;

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
