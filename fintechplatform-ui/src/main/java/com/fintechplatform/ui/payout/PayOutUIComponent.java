package com.fintechplatform.ui.payout;

import com.fintechplatform.ui.iban.ui.IbanUIModule;
import com.fintechplatform.ui.payout.ui.PayOutUI;
import com.fintechplatform.ui.payout.ui.PayOutUIModule;

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
