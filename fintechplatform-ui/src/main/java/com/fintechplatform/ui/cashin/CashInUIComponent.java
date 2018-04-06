package com.fintechplatform.ui.cashin;

import com.fintechplatform.ui.card.ui.PaymentCardUIModule;
import com.fintechplatform.ui.cashin.ui.CashInUI;
import com.fintechplatform.ui.cashin.ui.CashInUIModule;
import com.fintechplatform.ui.secure3d.ui.Secure3DUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules= {
        CashInUIModule.class,
        Secure3DUIModule.class,
        PaymentCardUIModule.class
})
public interface CashInUIComponent {
    CashInUI getPayInUI();
}
