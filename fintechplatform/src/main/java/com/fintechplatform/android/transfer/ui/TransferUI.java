package com.fintechplatform.android.transfer.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fintechplatform.android.models.DataAccount;

public class TransferUI {

    private DataAccount dataAccount;
    private String hostName;

    public static TransferUI instance;

    public TransferUI(DataAccount dataAccount, String hostName) {
        this.dataAccount = dataAccount;
        this.hostName = hostName;
    }



    public void start(Context context, Bundle networkUserIdArgs){
        instance = this;
        Intent intent = new Intent(context, TransferActivity.class);
        intent.putExtras(networkUserIdArgs);
        context.startActivity(intent);
    }
}
