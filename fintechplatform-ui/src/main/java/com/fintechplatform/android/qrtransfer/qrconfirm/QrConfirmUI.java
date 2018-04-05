package com.fintechplatform.android.qrtransfer.qrconfirm;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.card.ui.PaymentCardUI;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.payin.ui.PayInUI;
import com.fintechplatform.android.payin.ui.PayInUIModule;
import com.fintechplatform.android.payin.ui.PaymentCardUIModule;
import com.fintechplatform.android.payin.ui.Secure3DUIHelperModule;
import com.fintechplatform.android.secure3d.ui.Secure3DUI;
import com.fintechplatform.android.transfer.api.TransferAPIModule;

/**
 * Created by ingrid on 28/02/18.
 */

public class QrConfirmUI {

    private String hostName;
    private boolean sandbox;
    private DataAccount configuration;
    private PayInUI payInUI;
    private Secure3DUI secure3DUI;
    private PaymentCardUI paymentCardUI;


    protected static QrConfirmUI instance;

    public QrConfirmUI(String hostName, DataAccount configuration, PayInUI payInUI, Secure3DUI secure3DUI, PaymentCardUI paymentCardUI, boolean sandbox) {
        this.hostName = hostName;
        this.configuration = configuration;
        this.payInUI = payInUI;
        this.secure3DUI = secure3DUI;
        this.paymentCardUI = paymentCardUI;
        this.sandbox = sandbox;
    }

    protected QrConfirmComponent buildQrPaymentConfirmtComponent(Context context, QrConfirmContract.View v)  {
        return DaggerQrConfirmComponent.builder()
                .qrConfirmPresenterModule(new QrConfirmPresenterModule(v, configuration))
                .payInUIModule(new PayInUIModule(instance.hostName, configuration, sandbox))
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
