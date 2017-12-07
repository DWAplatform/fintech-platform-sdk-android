package com.dwaplatform.android.payin

import android.content.Context
import com.android.volley.toolbox.Volley
import com.dwaplatform.android.api.volley.VolleyRequestProvider
import com.dwaplatform.android.api.volley.VolleyRequestQueueProvider
import com.dwaplatform.android.card.helpers.JSONHelper
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.MoneyHelper
import com.dwaplatform.android.payin.api.PayInRestAPI
import com.dwaplatform.android.payin.ui.PayInUI

/**
 * Created by ingrid on 06/12/17.
 */
class PayIn constructor(val context: Context,
                        val hostname: String,
                        val token: String,
                        val moneyHelper: MoneyHelper?,
                        val feeHelper: FeeHelper? ) {

    fun getAPI(): PayInRestAPI {
        return PayInRestAPI(hostname, token,
                VolleyRequestQueueProvider(Volley.newRequestQueue(context)),
                VolleyRequestProvider(),
                JSONHelper())
    }

    fun getPayInUI(): PayInUI {
        return PayInUI(getAPI(), moneyHelper, feeHelper)
    }

}
