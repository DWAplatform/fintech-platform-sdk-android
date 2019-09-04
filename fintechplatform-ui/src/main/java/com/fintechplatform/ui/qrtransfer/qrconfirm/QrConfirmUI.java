package com.fintechplatform.ui.qrtransfer.qrconfirm;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.transfer.api.TransferAPIModule;
import com.fintechplatform.ui.models.DataAccount;

/**
 * Created by ingrid on 28/02/18.
 */

public class QrConfirmUI {

    private String hostName;
    private DataAccount configuration;

    protected static QrConfirmUI instance;

    public QrConfirmUI(String hostName, DataAccount configuration, boolean sandbox) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    protected QrConfirmComponent buildQrPaymentConfirmtComponent(Context context, QrConfirmContract.View v)  {
        return DaggerQrConfirmComponent.builder()
                .qrConfirmPresenterModule(new QrConfirmPresenterModule(v, configuration))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .transferAPIModule(new TransferAPIModule(hostName))
                .balanceAPIModule(new BalanceAPIModule(instance.hostName))
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
