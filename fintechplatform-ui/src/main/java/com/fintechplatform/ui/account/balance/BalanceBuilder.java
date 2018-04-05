package com.fintechplatform.ui.account.balance;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.net.NetModule;


/**
 * Created by tcappellari on 08/12/2017.
 */

public class BalanceBuilder {


    public BalanceHelperComponent createBalanceHelperComponent(String hostName, Context context) {
        return DaggerBalanceHelperComponent.builder()
                .balanceAPIModule(new BalanceAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }
}

