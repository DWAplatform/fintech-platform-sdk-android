package com.dwaplatform.android.payin.api

import android.content.Context
import com.dwaplatform.android.account.Account
import com.dwaplatform.android.models.Amount

/**
 * Created by ingrid on 06/12/17.
 */
class PayInAPI constructor(
        internal val context: Context,
        internal val hostName: String,
        internal val token: String) {



    fun payIn(amount: Amount, completion: (PayInReply, Error) -> Unit) {

    }

}