package com.fintechplatform.android.qrtransfer.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transfer.api.TransferAPIModule;

/**
 * Created by ingrid on 28/02/18.
 */

public class QrCreditTransferUI {
    private DataAccount dataAccount;
    private String hostName;

    public static QrCreditTransferUI instance;

    public QrCreditTransferUI(DataAccount dataAccount, String hostName) {
        this.dataAccount = dataAccount;
        this.hostName = hostName;
    }

    protected QrCreditTransferComponent createTransferComponent(Context context, QrReceiveActivityContract.View view){
        return DaggerQrCreditTransferComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .transferAPIModule(new TransferAPIModule(instance.hostName))
                .build();
    }

    public QrCreditTransferComponent buildTransferComponent(Context context, QrReceiveActivityContract.View view) {
        return instance.createTransferComponent(context, view);
    }
    public void start(Context context){
        instance = this;
        Intent intent = new Intent(context, QrReceiveActivity.class);
//        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
