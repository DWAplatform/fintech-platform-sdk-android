package com.dwaplatform.android.account.balance;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.account.balance.api.BalanceAPIModule;


/**
 * Created by tcappellari on 08/12/2017.
 */

public class BalanceBuilder {

    public BalanceAPIComponent createBalanceAPIComponent(String hostName, String token, Context context) {
        return DaggerBalanceAPIComponent.builder()
                .balanceAPIModule(new BalanceAPIModule(hostName,
                        token,
                        Volley.newRequestQueue(context)))
                .build();
    }


    public BalanceHelperComponent createBalanceHelperComponent(String hostName, String token, Context context) {
        return DaggerBalanceHelperComponent.builder()
                .balanceAPIModule(new BalanceAPIModule(hostName,
                        token,
                        Volley.newRequestQueue(context)))
                .build();
    }

}

