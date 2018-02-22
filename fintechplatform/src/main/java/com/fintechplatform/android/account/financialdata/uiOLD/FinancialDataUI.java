package com.fintechplatform.android.account.financialdata.uiOLD;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataContract;
import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataPresenterModule;
import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUI;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.card.api.PaymentCardAPIModule;
import com.fintechplatform.android.card.ui.PaymentCardUI;
import com.fintechplatform.android.iban.api.IbanAPIModule;
import com.fintechplatform.android.iban.ui.IbanUI;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.payin.ui.PaymentCardUIModule;
import com.fintechplatform.android.payout.ui.IbanUIModule;

public class FinancialDataUI {

    public static FinancialDataUI instance;
    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;
    private IbanUI ibanUI;
    private PaymentCardUI paymentCardUI;
    private PayInPayOutFinancialDataUI payInPayOutFinancialDataUI;

    protected FinancialDataUI() {}

    public FinancialDataUI(DataAccount configuration, String hostName, boolean isSandbox, IbanUI ibanUI, PaymentCardUI paymentCardUI, PayInPayOutFinancialDataUI payInPayOutFinancialDataUI) {
        this.configuration = configuration;
        this.ibanUI = ibanUI;
        this.paymentCardUI = paymentCardUI;
        this.hostName = hostName;
        this.isSandbox = isSandbox;
        this.payInPayOutFinancialDataUI = payInPayOutFinancialDataUI;
    }

    protected FinancialDataViewComponent createFinancialDataComponent(Context context, PayInPayOutFinancialDataContract.View view) {
        return DaggerFinancialDataViewComponent.builder()
                .payInPayOutFinancialDataPresenterModule(new PayInPayOutFinancialDataPresenterModule(view, instance.configuration))
                .ibanUIModule(new IbanUIModule(ibanUI))
                .paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
                .payInPayOutFinancialDataUIModule(new PayInPayOutFinancialDataUIModule(payInPayOutFinancialDataUI))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .paymentCardAPIModule(new PaymentCardAPIModule(instance.hostName, instance.isSandbox))
                .ibanAPIModule(new IbanAPIModule(instance.hostName))
                .build();
    }

    public FinancialDataViewComponent buildFinancialDataViewComponent(Context context, PayInPayOutFinancialDataContract.View view) {
        return instance.createFinancialDataComponent(context,view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, FinancialDataActivity.class);
        context.startActivity(intent);
    }
}
