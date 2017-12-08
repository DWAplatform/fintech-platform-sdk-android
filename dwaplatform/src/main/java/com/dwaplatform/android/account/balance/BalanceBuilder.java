package com.dwaplatform.android.account.balance;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.account.balance.helpers.BalanceHelperModule;
import com.dwaplatform.android.payin.PayInAPIComponent;
import com.dwaplatform.android.payin.PayInUIComponent;
import com.dwaplatform.android.payin.api.PayInAPIModule;
import com.dwaplatform.android.payin.models.PayInConfiguration;
import com.dwaplatform.android.payin.ui.PayInUIModule;

/**
 * Created by tcappellari on 08/12/2017.
 */

public class BalanceBuilder {

    public BalanceAPIComponent createBalanceAPIComponent(String hostName, String token, Context context) {
        return DaggerPayInAPIComponent.builder()
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

