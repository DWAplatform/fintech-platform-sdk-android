package com.dwaplatform.android.payin

import android.content.Context
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.MoneyHelper
import com.dwaplatform.android.payin.api.PayInAPI
import com.dwaplatform.android.payin.ui.PayInUI

/**
 * Created by ingrid on 06/12/17.
 */
class PayIn constructor(context: Context,
                        api: PayInAPI,
                        moneyHelper: MoneyHelper?,
                        feeHelper: FeeHelper? ) {



    fun getAPI(context: Context,hostname: String, token: String): PayInAPI {
        return PayInAPI(context,hostname, token)
    }

    fun getUI(api: PayInAPI, moneyHelper: MoneyHelper?, feeHelper: FeeHelper?): PayInUI {
        return PayInUI()
    }

}
