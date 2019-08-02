package com.fintechplatform.ui.transfer.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.transfer.api.TransferAPIModule;
import com.fintechplatform.ui.models.DataAccount;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class TransferUI {

    private DataAccount dataAccount;
    private String hostName;

    public static TransferUI instance;

    public TransferUI(DataAccount dataAccount, String hostName) {
        this.dataAccount = dataAccount;
        this.hostName = hostName;
    }

    protected TransferComponent createTransferComponent(Context context, TransferContract.View view){
        return DaggerTransferComponent.builder()
                .balanceAPIModule(new BalanceAPIModule(instance.hostName))
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), instance.hostName)))
                .transferPresenterModule(new TransferPresenterModule(view, instance.dataAccount))
                .transferAPIModule(new TransferAPIModule(instance.hostName))
                .build();
    }

    public TransferComponent buildTransferComponent(Context context, TransferContract.View view) {
        return instance.createTransferComponent(context, view);
    }
    public void start(Context context, Bundle networkUserIdArgs){
        instance = this;
        Intent intent = new Intent(context, TransferActivity.class);
        intent.putExtras(networkUserIdArgs);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
