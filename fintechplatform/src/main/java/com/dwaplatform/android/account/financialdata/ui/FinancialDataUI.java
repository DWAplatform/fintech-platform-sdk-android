package com.dwaplatform.android.account.financialdata.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataContract;
import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataPresenterModule;
import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUI;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.api.PaymentCardAPIModule;
import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.iban.api.IbanAPIModule;
import com.dwaplatform.android.iban.ui.IbanUI;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.payin.ui.PaymentCardUIModule;
import com.dwaplatform.android.payout.ui.IbanUIModule;

/**
 * Created by ingrid on 20/01/18.
 */

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
