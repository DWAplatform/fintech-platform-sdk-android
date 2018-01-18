package com.dwaplatform.android.account.financialdata.ui;

import android.content.Context;
import android.content.Intent;

import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.iban.ui.IbanUI;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.payin.ui.PaymentCardUIModule;
import com.dwaplatform.android.payout.ui.IbanUIModule;

/**
 * Created by ingrid on 18/01/18.
 */

public class FinancialDataUI {

    public static FinancialDataUI instance;
    private IbanUI ibanUI;
    private PaymentCardUI paymentCardUI;

    protected FinancialDataUI() {}

    public FinancialDataUI(IbanUI ibanUI, PaymentCardUI paymentCardUI) {
        this.ibanUI = ibanUI;
        this.paymentCardUI = paymentCardUI;
    }

    protected FinancialDataViewComponent createFinancialDataComponent(FinancialDataContract.View view) {
        return DaggerFinancialDataViewComponent.builder()
                .financialDataPresenterModule(new FinancialDataPresenterModule(view))
                .ibanUIModule(new IbanUIModule(ibanUI))
                .paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
                .build();
    }

    public FinancialDataViewComponent buildFinancialDataViewComponent(FinancialDataContract.View view) {
        return instance.createFinancialDataComponent(view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, FinancialDataActivity.class);
        context.startActivity(intent);
    }
}
