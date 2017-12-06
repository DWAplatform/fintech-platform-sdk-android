package com.dwaplatform.android.payin.api

import com.dwaplatform.android.account.Account
import com.dwaplatform.android.models.Amount
import com.dwaplatform.android.payin.models.PayInReply

/**
 * Created by ingrid on 06/12/17.
 */
class PayInAPI constructor( internal val restAPI: PayInRestAPI ) {


    open fun payIn(account: Account, completion: (PayInReply, Error) -> Unit) {
        restAPI.payIn(account, completion)
    }

}