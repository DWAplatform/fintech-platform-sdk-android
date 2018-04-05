package com.fintechplatform.api.payin;

import android.content.Context;

import com.fintechplatform.api.payin.ui.DaggerMockPayInComponent;
import com.fintechplatform.api.payin.ui.PayInUI;
import com.fintechplatform.api.payin.ui.PayInViewComponent;

public class PayInUIMockViewComponent extends PayInUI {

    public PayInUIMockViewComponent() {
        instance = this;
    }

   @Override
    protected PayInViewComponent buildPayInViewComponent(Context context, PayInContract.View v)  {
        return DaggerMockPayInComponent.builder().build();
    }

}
