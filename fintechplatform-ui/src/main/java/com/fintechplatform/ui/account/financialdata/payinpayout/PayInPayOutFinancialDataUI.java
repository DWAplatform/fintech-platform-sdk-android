package com.fintechplatform.ui.account.financialdata.payinpayout;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.card.ui.PaymentCardUI;
import com.fintechplatform.ui.cashin.ui.PaymentCardUIModule;
import com.fintechplatform.ui.iban.ui.IbanUI;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.payout.ui.IbanUIModule;

public class PayInPayOutFinancialDataUI {

    public static PayInPayOutFinancialDataUI instance;
    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;
    private IbanUI ibanUI;
    private PaymentCardUI paymentCardUI;

    public PayInPayOutFinancialDataUI(DataAccount configuration, String hostName, boolean isSandbox, IbanUI ibanUI, PaymentCardUI paymentCardUI) {
        this.configuration = configuration;
        this.ibanUI = ibanUI;
        this.paymentCardUI = paymentCardUI;
        this.hostName = hostName;
        this.isSandbox = isSandbox;
    }

    protected PayInPayOutFinancialDataViewComponent createFinancialDataComponent(Context context, FinancialDataContract.View view) {
        return DaggerPayInPayOutFinancialDataViewComponent.builder()
                .payInPayOutFinancialDataPresenterModule(new PayInPayOutFinancialDataPresenterModule(view, instance.configuration))
                .ibanUIModule(new IbanUIModule(ibanUI))
                .paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .paymentCardAPIModule(new PaymentCardAPIModule(instance.hostName, instance.isSandbox))
                .ibanAPIModule(new IbanAPIModule(instance.hostName))
                .build();
    }

    public PayInPayOutFinancialDataViewComponent buildFinancialDataViewComponent(Context context, FinancialDataContract.View view) {
        return instance.createFinancialDataComponent(context,view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, FinancialDataActivity.class);
        context.startActivity(intent);
    }
}
