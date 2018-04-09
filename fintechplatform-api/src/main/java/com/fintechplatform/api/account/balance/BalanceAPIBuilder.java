package com.fintechplatform.api.account.balance;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.net.NetModule;

public class BalanceAPIBuilder {

    public BalanceAPIComponent createBalanceAPIComponent(String hostName, Context context) {
        return DaggerBalanceAPIComponent.builder()
                .balanceAPIModule(new BalanceAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }
}

