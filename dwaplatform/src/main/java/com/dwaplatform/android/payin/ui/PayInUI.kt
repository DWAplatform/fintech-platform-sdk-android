package com.dwaplatform.android.payin.ui

import android.content.Context
import android.content.Intent
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.MoneyHelper
import com.dwaplatform.android.payin.PayInActivity
import com.dwaplatform.android.payin.api.PayInRestAPI

/**
 * Created by ingrid on 06/12/17.
 */
class PayInUI constructor(val api: PayInRestAPI,
                          val moneyHelper: MoneyHelper?,
                          val feeHelper: FeeHelper?){

    open fun start(context: Context) {
        val intent = Intent(context, PayInActivity::class.java)
        context.startActivity(intent)
    }

}