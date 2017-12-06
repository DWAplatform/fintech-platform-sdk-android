package com.dwaplatform.android.payin.ui

import android.content.Context
import android.content.Intent
import com.dwafintech.dwapay.financial.payin.PayInActivity
import com.dwafintech.dwapay.financial.payin.PayInPresenter
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.MoneyHelper
import com.dwaplatform.android.payin.api.PayInAPI

/**
 * Created by ingrid on 06/12/17.
 */
class PayInUI constructor(val api: PayInAPI,

                          val moneyHelper: MoneyHelper?,
                          val feeHelper: FeeHelper?){

    open fun start(context: Context) {
        val intent = Intent(context, PayInActivity::class.java)
    }

}