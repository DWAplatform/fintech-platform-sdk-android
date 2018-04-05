package com.fintechplatform.ui.payin;

import com.fintechplatform.ui.card.ui.PaymentCardUIModule;
import com.fintechplatform.ui.payin.ui.PayInUI;
import com.fintechplatform.ui.payin.ui.PayInUIModule;
import com.fintechplatform.ui.secure3d.ui.Secure3DUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules= {
        PayInUIModule.class,
        Secure3DUIModule.class,
        PaymentCardUIModule.class
})
public interface PayInUIComponent {
    PayInUI getPayInUI();
}
