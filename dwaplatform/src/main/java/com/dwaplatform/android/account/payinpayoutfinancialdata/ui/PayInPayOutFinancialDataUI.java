package com.dwaplatform.android.account.payinpayoutfinancialdata.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.api.PaymentCardAPIModule;
import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.iban.api.IbanAPIModule;
import com.dwaplatform.android.iban.ui.IbanUI;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.payin.ui.PaymentCardUIModule;
import com.dwaplatform.android.payout.ui.IbanUIModule;

public class PayInPayOutFinancialDataUI {

    public static PayInPayOutFinancialDataUI instance;
    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;
    private IbanUI ibanUI;
    private PaymentCardUI paymentCardUI;

    protected PayInPayOutFinancialDataUI() {}

    public PayInPayOutFinancialDataUI(DataAccount configuration, String hostName, boolean isSandbox, IbanUI ibanUI, PaymentCardUI paymentCardUI) {
        this.configuration = configuration;
        this.ibanUI = ibanUI;
        this.paymentCardUI = paymentCardUI;
        this.hostName = hostName;
        this.isSandbox = isSandbox;
    }

    protected PayInPayOutFinancialDataViewComponent createFinancialDataComponent(Context context, PayInPayOutFinancialDataContract.View view) {
        return DaggerPayInPayOutFinancialDataViewComponent.builder()
                .payInPayOutFinancialDataPresenterModule(new PayInPayOutFinancialDataPresenterModule(view, instance.configuration))
                .ibanUIModule(new IbanUIModule(ibanUI))
                .paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .paymentCardAPIModule(new PaymentCardAPIModule(instance.hostName, instance.isSandbox))
                .ibanAPIModule(new IbanAPIModule(instance.hostName))
                .build();
    }

    public PayInPayOutFinancialDataViewComponent buildFinancialDataViewComponent(Context context, PayInPayOutFinancialDataContract.View view) {
        return instance.createFinancialDataComponent(context,view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, PayInPayOutFinancialDataActivity.class);
        context.startActivity(intent);
    }
}
