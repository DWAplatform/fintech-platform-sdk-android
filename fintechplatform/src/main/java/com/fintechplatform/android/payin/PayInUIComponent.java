package com.fintechplatform.android.payin;

import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.payin.ui.PayInUI;
import com.fintechplatform.android.payin.ui.PayInUIModule;
import com.fintechplatform.android.secure3d.ui.Secure3DUIModule;

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
