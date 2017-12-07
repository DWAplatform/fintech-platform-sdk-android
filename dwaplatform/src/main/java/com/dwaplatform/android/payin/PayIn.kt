package com.dwaplatform.android.payin

import com.dwaplatform.android.account.Account
import android.content.Context
import com.android.volley.toolbox.Volley
import com.dwaplatform.android.account.balance.Balance
import com.dwaplatform.android.acquiringchannels.PaymentCard
import com.dwaplatform.android.api.volley.VolleyRequestProvider
import com.dwaplatform.android.api.volley.VolleyRequestQueueProvider
import com.dwaplatform.android.card.helpers.JSONHelper
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.MoneyHelper
import com.dwaplatform.android.payin.api.PayInRestAPI
import com.dwaplatform.android.payin.ui.PayInPresenterFactory
import com.dwaplatform.android.payin.ui.PayInUI

/**
 * Created by ingrid on 06/12/17.
 */
class PayInFactory {

    open fun buildAPI(token: String, hostname: String, context: Context, account: Account, balance: Balance, paymentCard: PaymentCard): PayInRestAPI {
        return PayInRestAPI(hostname, token,
                VolleyRequestQueueProvider(Volley.newRequestQueue(context)),
                VolleyRequestProvider(),
                JSONHelper(),
                account,
                balance,
                paymentCard)
    }

    open fun buildPayInUI(payIn: PayIn, api: PayInRestAPI, moneyHelper: MoneyHelper, feeHelper: FeeHelper, presenterFactory: PayInPresenterFactory): PayInUI {
        return PayInUI(payIn, api, moneyHelper, feeHelper, presenterFactory)
    }

}

class PayIn constructor(val account: Account, val balance: Balance, val paymentCard: PaymentCard)
