package com.dwaplatform.android.payin;

import android.content.Context;

import com.dwaplatform.android.payin.ui.DaggerMockPayInComponent;
import com.dwaplatform.android.payin.ui.PayInUI;
import com.dwaplatform.android.payin.ui.PayInViewComponent;
import com.dwaplatform.android.payin.ui.Secure3DModule;
import com.dwaplatform.android.secure3d.ui.Secure3DMockUI;

/**
 * Created by ingrid on 12/12/17.
 */

public class PayInUIMockViewComponent extends PayInUI {

    public PayInUIMockViewComponent() {
        instance = this;
    }

   @Override
    protected PayInViewComponent buildPayInViewComponent(Context context, PayInContract.View v)  {
        return DaggerMockPayInComponent.builder()
                .secure3DUIModule(new Secure3DModule(new Secure3DMockUI()))
                .build();
    }

}
