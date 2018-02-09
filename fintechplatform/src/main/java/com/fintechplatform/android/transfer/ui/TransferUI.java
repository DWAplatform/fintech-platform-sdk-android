package com.fintechplatform.android.transfer.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transfer.api.TransferAPIModule;

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
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
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
        context.startActivity(intent);
    }
}
