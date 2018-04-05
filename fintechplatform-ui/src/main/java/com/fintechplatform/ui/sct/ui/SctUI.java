package com.fintechplatform.ui.sct.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.api.net.NetModule;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SctUI {

    private DataAccount dataAccount;
    private String hostName;

    public static SctUI instance;

    public SctUI(DataAccount dataAccount, String hostName) {
        this.dataAccount = dataAccount;
        this.hostName = hostName;
    }

    protected SctComponent createSctComponent(Context context, SctContract.View view){
        return DaggerSctComponent.builder()
                .balanceAPIModule(new BalanceAPIModule(instance.hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .sctPresenterModule(new SctPresenterModule(view, instance.dataAccount))
                .build();
    }

    public SctComponent buildSctComponent(Context context, SctContract.View view) {
        return instance.createSctComponent(context, view);
    }
    public void start(Context context, Bundle args){
        instance = this;
        Intent intent = new Intent(context, SctActivity.class);
        intent.putExtras(args);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
