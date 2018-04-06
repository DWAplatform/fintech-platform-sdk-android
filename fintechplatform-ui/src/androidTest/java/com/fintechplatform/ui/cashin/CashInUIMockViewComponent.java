package com.fintechplatform.ui.cashin;

import android.content.Context;

import com.fintechplatform.ui.cashin.ui.CashInUI;
import com.fintechplatform.ui.cashin.ui.CashInViewComponent;
import com.fintechplatform.ui.cashin.ui.DaggerMockPayInComponent;

public class CashInUIMockViewComponent extends CashInUI {

    public CashInUIMockViewComponent() {
        instance = this;
    }

   @Override
    protected CashInViewComponent buildPayInViewComponent(Context context, CashInContract.View v)  {
        return DaggerMockPayInComponent.builder().build();
    }

}
