package com.dwaplatform.android.money


import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by tcappellari on 08/12/2017.
 */
@Module
class MoneyHelperModule {
    @Provides
    @Singleton
    internal fun provideMoneyHelper(): MoneyHelper {
        return MoneyHelper()
    }
}
