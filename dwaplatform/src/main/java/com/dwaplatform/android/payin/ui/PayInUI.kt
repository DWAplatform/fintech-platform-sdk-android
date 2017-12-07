package com.dwaplatform.android.payin.ui

import android.content.Context
import android.content.Intent
import com.dwaplatform.android.account.Account
import com.dwaplatform.android.acquiringchannels.PaymentCard
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.MoneyHelper
import com.dwaplatform.android.payin.PayIn
import com.dwaplatform.android.payin.PayInActivity
import com.dwaplatform.android.payin.PayInContract
import com.dwaplatform.android.payin.PayInPresenter
import com.dwaplatform.android.payin.api.PayInRestAPI

/**
 * Created by ingrid on 06/12/17.
 */
open class PayInUI constructor(val payIn: PayIn,
                          val api: PayInRestAPI,
                          val moneyHelper: MoneyHelper,
                          val feeHelper: FeeHelper,
                          val presenter: PayInPresenterFactory){

    companion object {
        var payInUiInstance: PayInUI? = null
    }

    open fun start(context: Context) {
        payInUiInstance = this
        val intent = Intent(context, PayInActivity::class.java)
        context.startActivity(intent)
    }

}

open class PayInPresenterFactory {

    open fun build(context: PayInContract.View, api: PayInRestAPI, account: Account, moneyHelper: MoneyHelper, feeHelper: FeeHelper, paymentCard: PaymentCard): PayInContract.Presenter {
        return PayInPresenter(context, api, account, moneyHelper, feeHelper, paymentCard)
    }

}
