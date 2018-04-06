package com.fintechplatform.api.cashin;

import android.content.Context;

import com.fintechplatform.api.cashin.ui.DaggerMockPayInComponent;
import com.fintechplatform.api.cashin.ui.PayInUI;
import com.fintechplatform.api.cashin.ui.PayInViewComponent;

public class PayInUIMockViewComponent extends PayInUI {

    public PayInUIMockViewComponent() {
        instance = this;
    }

   @Override
    protected PayInViewComponent buildPayInViewComponent(Context context, PayInContract.View v)  {
        return DaggerMockPayInComponent.builder().build();
    }

}
