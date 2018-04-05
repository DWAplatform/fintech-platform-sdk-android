package com.fintechplatform.api.account.balance.api

import com.android.volley.Request
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import org.json.JSONException
import javax.inject.Inject

open class BalanceAPI @Inject constructor(internal val hostName: String,
                                        internal val queue: IRequestQueue,
                                        internal val requestProvider: IRequestProvider,
                                        internal val netHelper: NetHelper,
                                        internal val log: Log) {

    private final val TAG = "BalanceAPI"

    open fun balance(token: String, ownerId: String, accountId: String, accountType: String, tenantId: String, completion: (Money?, Exception?) -> Unit): IRequest<*>? {



        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$ownerId/accounts/$accountId/balance")

        var request: IRequest<*>?
        try {

            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token), { response ->
                try {
                    val balance = response.getJSONObject("balance")
                    val moneyBalance = Money(balance.getLong("amount"), balance.getString("currency"))

//                  TODO  handle availableMoney
                    val availableBalance = response.getJSONObject("availableBalance")
                    val moneyAvailable = Money(availableBalance.getLong("amount"), availableBalance.getString("currency"))

                    completion(moneyBalance, null)
                } catch (e: JSONException) {
                    completion(null, netHelper.ReplyParamsUnexpected(e))
                }
            }
            ) { error ->
                completion(null, error)
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "balance", e)
            request = null
        }

        return request

    }


}