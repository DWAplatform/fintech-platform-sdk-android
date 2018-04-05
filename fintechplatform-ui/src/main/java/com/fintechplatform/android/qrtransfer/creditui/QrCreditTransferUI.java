package com.fintechplatform.android.qrtransfer.creditui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.net.NetModule;
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

    protected QrCreditTransferComponent createTransferComponent(Context context, QrReceiveActivityContract.View view, QrReceiveAmountContract.View amountView, QrReceiveShowContract.View showView){
        return DaggerQrCreditTransferComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .transferAPIModule(new TransferAPIModule(instance.hostName))
                .qrReceivePresenterModule(new QrReceivePresenterModule(view, amountView, showView, dataAccount))
                .build();
    }

    public QrCreditTransferComponent buildQrComponent(Context context, QrReceiveActivityContract.View view) {
        return instance.createTransferComponent(context, view, null, null);
    }

    public QrCreditTransferComponent buildQrAmountComponent(Context context, QrReceiveAmountContract.View view) {
        return instance.createTransferComponent(context, null, view, null);
    }

    public QrCreditTransferComponent buildQrShowComponent(Context context, QrReceiveShowContract.View view) {
        return instance.createTransferComponent(context, null, null, view);
    }

    public void start(Context context){
        instance = this;
        Intent intent = new Intent(context, QrReceiveActivity.class);
//        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
