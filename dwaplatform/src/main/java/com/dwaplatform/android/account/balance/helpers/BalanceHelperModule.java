package com.dwaplatform.android.account.balance.helpers;

import com.dwaplatform.android.account.balance.api.BalanceAPI;
import com.dwaplatform.android.account.balance.db.BalancePersistence;
import com.dwaplatform.android.account.balance.db.DBBalance;
import com.dwaplatform.android.account.balance.db.DBBalancePersistence;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class BalanceHelperModule {

    @Provides
    @Singleton
    DBBalance provideDBBalance() {
        return new DBBalance();
    }

    @Provides
    @Singleton
    BalancePersistence provideBalancePersistence(DBBalance dbBalance) {
        return new DBBalancePersistence(dbBalance);
    }

    @Provides
    @Singleton
    BalanceHelper provideBalanceHelper(BalancePersistence persistence,
                                       BalanceAPI api) {
        return new BalanceHelper(persistence, api);
    }

}
