package com.fintechplatform.android.payin;

import android.content.Context;

import com.fintechplatform.android.payin.ui.DaggerMockPayInComponent;
import com.fintechplatform.android.payin.ui.PayInUI;
import com.fintechplatform.android.payin.ui.PayInViewComponent;

public class PayInUIMockViewComponent extends PayInUI {

    public PayInUIMockViewComponent() {
        instance = this;
    }

   @Override
    protected PayInViewComponent buildPayInViewComponent(Context context, PayInContract.View v)  {
        return DaggerMockPayInComponent.builder().build();
    }

}
