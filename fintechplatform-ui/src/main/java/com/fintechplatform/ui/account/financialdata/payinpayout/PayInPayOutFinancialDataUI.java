package com.fintechplatform.ui.account.financialdata.payinpayout;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.models.DataAccount;

public class PayInPayOutFinancialDataUI {

    public static PayInPayOutFinancialDataUI instance;
    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;

    public PayInPayOutFinancialDataUI(DataAccount configuration, String hostName, boolean isSandbox) {
        this.configuration = configuration;
        this.hostName = hostName;
        this.isSandbox = isSandbox;
    }

    protected PayInPayOutFinancialDataViewComponent createFinancialDataComponent(Context context, FinancialDataContract.View view) {
        return DaggerPayInPayOutFinancialDataViewComponent.builder()
                .payInPayOutFinancialDataPresenterModule(new PayInPayOutFinancialDataPresenterModule(view, instance.configuration))
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
