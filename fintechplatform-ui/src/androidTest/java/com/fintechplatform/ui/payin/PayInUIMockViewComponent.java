package com.fintechplatform.ui.payin;

import android.content.Context;

import com.fintechplatform.ui.payin.ui.DaggerMockPayInComponent;
import com.fintechplatform.ui.payin.ui.PayInUI;
import com.fintechplatform.ui.payin.ui.PayInViewComponent;

public class PayInUIMockViewComponent extends PayInUI {

    public PayInUIMockViewComponent() {
        instance = this;
    }

   @Override
    protected PayInViewComponent buildPayInViewComponent(Context context, PayInContract.View v)  {
        return DaggerMockPayInComponent.builder().build();
    }

}
