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
/**
 * Balance API class performs request to Fintech Platform to get balance information.
 */
open class BalanceAPI @Inject constructor(internal val hostName: String,
                                        internal val queue: IRequestQueue,
                                        internal val requestProvider: IRequestProvider,
                                        internal val netHelper: NetHelper,
                                        internal val log: Log) {

    private final val TAG = "BalanceAPI"

    /**
     * Balance represent the total amount of money that User [ownerId] has in his own Fintech Account, identified from [tenantId] [accountType] and [accountId] params.
     * Use [token] got from "Create User token" request.
     * [completion] is a list of balances, first item is the current balance, second item is the available balance
     */
    open fun balance(token: String,
                     ownerId: String,
                     accountId: String,
                     accountType: String,
                     tenantId: String,
                     completion: (List<Money>?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$ownerId/accounts/$accountId/balance")

        var request: IRequest<*>?
        try {

            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token), { response ->
                try {
                    val balanceList = arrayListOf<Money>()
                    val balance = response.getJSONObject("balance")
                    val moneyBalance = Money(balance.getLong("amount"), balance.getString("currency"))

                    balanceList.add(moneyBalance)

                    val availableBalance = response.getJSONObject("availableBalance")
                    val moneyAvailable = Money(availableBalance.getLong("amount"), availableBalance.getString("currency"))

                    balanceList.add(moneyAvailable)
                    completion(balanceList, null)
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