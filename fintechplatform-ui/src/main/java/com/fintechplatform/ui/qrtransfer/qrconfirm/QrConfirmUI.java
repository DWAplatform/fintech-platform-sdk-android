package com.fintechplatform.ui.qrtransfer.qrconfirm;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.transfer.api.TransferAPIModule;
import com.fintechplatform.ui.card.ui.PaymentCardUI;
import com.fintechplatform.ui.cashin.ui.CashInUI;
import com.fintechplatform.ui.cashin.ui.CashInUIModule;
import com.fintechplatform.ui.cashin.ui.PaymentCardUIModule;
import com.fintechplatform.ui.cashin.ui.Secure3DUIHelperModule;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.secure3d.ui.Secure3DUI;

/**
 * Created by ingrid on 28/02/18.
 */

public class QrConfirmUI {

    private String hostName;
    private boolean sandbox;
    private DataAccount configuration;
    private CashInUI cashInUI;
    private Secure3DUI secure3DUI;
    private PaymentCardUI paymentCardUI;


    protected static QrConfirmUI instance;

    public QrConfirmUI(String hostName, DataAccount configuration, CashInUI cashInUI, Secure3DUI secure3DUI, PaymentCardUI paymentCardUI, boolean sandbox) {
        this.hostName = hostName;
        this.configuration = configuration;
        this.cashInUI = cashInUI;
        this.secure3DUI = secure3DUI;
        this.paymentCardUI = paymentCardUI;
        this.sandbox = sandbox;
    }

    protected QrConfirmComponent buildQrPaymentConfirmtComponent(Context context, QrConfirmContract.View v)  {
        return DaggerQrConfirmComponent.builder()
                .qrConfirmPresenterModule(new QrConfirmPresenterModule(v, configuration))
                .cashInUIModule(new CashInUIModule(instance.hostName, configuration, sandbox))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .transferAPIModule(new TransferAPIModule(hostName))
                .balanceAPIModule(new BalanceAPIModule(instance.hostName))
                .secure3DUIHelperModule(new Secure3DUIHelperModule(secure3DUI))
                .paymentCardUIModule(new PaymentCardUIModule(paymentCardUI))
                .build();
    }

    public static QrConfirmComponent createPaymentConfirmComponent(Context context, QrConfirmContract.View v)  {
        return instance.buildQrPaymentConfirmtComponent(context, v);
    }

    public void start(Context context, String qrCode) {
        instance = this;
        Intent intent = new Intent(context, QrConfirmActivity.class);
        intent.putExtra("qrCode", qrCode);
        context.startActivity(intent);
    }
}
