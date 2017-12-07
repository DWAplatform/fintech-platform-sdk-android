package com.dwaplatform.android.account.balance

import android.content.Context
import com.android.volley.toolbox.Volley
import com.dwafintech.dwapay.model.Money
import com.dwaplatform.android.account.Account
import com.dwaplatform.android.account.balance.api.BalanceApi
import com.dwaplatform.android.account.balance.db.DBBalance
import com.dwaplatform.android.account.balance.db.DBBalanceHelper
import com.dwaplatform.android.api.volley.VolleyRequestProvider
import com.dwaplatform.android.api.volley.VolleyRequestQueueProvider
import com.dwaplatform.android.card.helpers.JSONHelper
import com.dwaplatform.android.payin.api.PayInRestAPI

/**
 * Created by ingrid on 06/12/17.
 */
class BalanceFactory constructor()  {

    open fun buildAPI(token: String, hostname: String, context: Context, account: Account): BalanceApi {
        return BalanceApi(hostname, token,
                VolleyRequestQueueProvider(Volley.newRequestQueue(context)),
                VolleyRequestProvider(),
                JSONHelper(),
                account)
    }

    open fun buildBalanceHelper(): BalanceHelper {
        return DBBalanceHelper(DBBalance())
    }

}

class Balance(val account: Account, val api: BalanceApi, val balanceHelper: BalanceHelper) {

    fun getBalance(callback: (Money?, Exception?) -> Unit): Money {


        api.balance {  optbalance, opterror ->
            if (opterror != null) {
                callback(null, opterror)
                return@balance
            }

            if (optbalance == null) {
                callback(null, Exception("Balance null"))
                return@balance
            }

            val bal = optbalance
            val money = Money(bal)
            balanceHelper.saveBalance(BalanceItem(account.id, money))
            callback(money, null)
        }

        return balanceHelper.getBalanceItem(account.id)?.money ?: Money(0)


    }

}
