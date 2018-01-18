package com.dwaplatform.android.account.payinpayoutfinancialdata.ui;

import android.content.Context;
import android.content.Intent;

import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.iban.ui.IbanUI;
import com.dwaplatform.android.payin.ui.PaymentCardUIModule;
import com.dwaplatform.android.payout.ui.IbanUIModule;

/**
 * Created by ingrid on 18/01/18.
 */

public class PayInPayOutFinancialDataUI {

    public static PayInPayOutFinancialDataUI instance;
    private IbanUI ibanUI;
    private PaymentCardUI paymentCardUI;

    protected PayInPayOutFinancialDataUI() {}

    public PayInPayOutFinancialDataUI(IbanUI ibanUI, PaymentCardUI paymentCardUI) {
        this.ibanUI = ibanUI;
        this.paymentCardUI = paymentCardUI;
    }

    protected PayInPayOutFinancialDataViewComponent createFinancialDataComponent(PayInPayOutFinancialDataContract.View view) {
        return DaggerPayInPayOutFinancialDataViewComponent.builder()
                .payInPayOutFinancialDataPresenterModule(new PayInPayOutFinancialDataPresenterModule(view))
                .ibanUIModule(new IbanUIModule(ibanUI))
                .paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
                .build();
    }

    public PayInPayOutFinancialDataViewComponent buildFinancialDataViewComponent(PayInPayOutFinancialDataContract.View view) {
        return instance.createFinancialDataComponent(view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, PayInPayOutFinancialDataActivity.class);
        context.startActivity(intent);
    }
}
