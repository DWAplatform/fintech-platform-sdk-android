package com.dwaplatform.android.account.balance;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.api.NetModule;


/**
 * Created by tcappellari on 08/12/2017.
 */

public class BalanceBuilder {

    public BalanceAPIComponent createBalanceAPIComponent(String hostName, Context context) {
        return DaggerBalanceAPIComponent.builder()
                .balanceAPIModule(new BalanceAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }


    public BalanceHelperComponent createBalanceHelperComponent(String hostName, Context context) {
        return DaggerBalanceHelperComponent.builder()
                .balanceAPIModule(new BalanceAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }
}

