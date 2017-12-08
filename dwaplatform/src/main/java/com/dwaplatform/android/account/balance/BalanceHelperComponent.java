package com.dwaplatform.android.account.balance;

import com.dwaplatform.android.account.balance.api.BalanceAPI;
import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.account.balance.helpers.BalanceHelper;
import com.dwaplatform.android.account.balance.helpers.BalanceHelperModule;
import com.dwaplatform.android.log.LogModule;

/**
 * Created by tcappellari on 08/12/2017.
 */


@Singleton
@Component(modules= {
        BalanceAPIModule.class,
        BalanceHelperModule.class,

})
public interface BalanceHelperComponent {

    BalanceHelper getBalanceHelper();
}


