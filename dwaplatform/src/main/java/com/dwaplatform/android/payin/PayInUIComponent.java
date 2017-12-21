package com.dwaplatform.android.payin;

import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;
import com.dwaplatform.android.payin.ui.PayInUI;
import com.dwaplatform.android.payin.ui.PayInUIModule;
import com.dwaplatform.android.secure3d.ui.Secure3DUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Singleton
@Component(modules= {
        PayInUIModule.class,
        Secure3DUIModule.class,
        PaymentCardUIModule.class
})
public interface PayInUIComponent {
    PayInUI getPayInUI();
}
