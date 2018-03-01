package com.fintechplatform.android.account.financialdata.bank;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.account.financialdata.payinpayout.FinancialDataContract;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.card.api.PaymentCardAPIModule;
import com.fintechplatform.android.card.ui.PaymentCardUI;
import com.fintechplatform.android.iban.api.IbanAPIModule;
import com.fintechplatform.android.iban.ui.IbanUI;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.payin.ui.PaymentCardUIModule;
import com.fintechplatform.android.payout.ui.IbanUIModule;

public class BankFinancialDataUI {

    public static BankFinancialDataUI instance;
    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;
    private IbanUI ibanUI;
    private PaymentCardUI paymentCardUI;


    public BankFinancialDataUI(DataAccount configuration, String hostName, boolean isSandbox, IbanUI ibanUI, PaymentCardUI paymentCardUI) {
        this.configuration = configuration;
        this.ibanUI = ibanUI;
        this.paymentCardUI = paymentCardUI;
        this.hostName = hostName;
        this.isSandbox = isSandbox;
    }

    protected BankFinancialDataViewComponent createFinancialDataComponent(Context context, FinancialDataContract.View view) {
        return DaggerBankFinancialDataViewComponent.builder()
                .bankFinancialDataPresenterModule(new BankFinancialDataPresenterModule(view,instance.configuration))
                .ibanUIModule(new IbanUIModule(ibanUI))
                .paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .paymentCardAPIModule(new PaymentCardAPIModule(instance.hostName, instance.isSandbox))
                .ibanAPIModule(new IbanAPIModule(instance.hostName))
                .build();
    }

    public BankFinancialDataViewComponent buildFinancialDataViewComponent(Context context, FinancialDataContract.View view) {
        return instance.createFinancialDataComponent(context,view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, BankFinancialDataActivity.class);
        context.startActivity(intent);
    }
}
