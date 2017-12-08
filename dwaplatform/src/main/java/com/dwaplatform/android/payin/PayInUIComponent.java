package com.dwaplatform.android.payin;

import com.dwaplatform.android.payin.api.PayInAPI;
import com.dwaplatform.android.payin.api.PayInAPIModule;
import com.dwaplatform.android.payin.ui.PayInUI;
import com.dwaplatform.android.payin.ui.PayInUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Singleton
@Component(modules= {
        PayInUIModule.class

})
public interface PayInUIComponent {

    PayInUI getPayInUI();
}
