package com.fintechplatform.api.transactions.api

import com.android.volley.Request
import com.fintechplatform.api.account.models.AccountType
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.transactions.models.TransactionResponse
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import javax.inject.Inject

/**
 * TransactionsAPI class performs request to Fintech Platform to get detailed transactions information.
 */
class TransactionsAPI @Inject constructor(
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log,
        val netHelper: NetHelper ) {

    private val TAG = "TransactionsAPI"

    /**
     * Get transactions (cash in, cash out, purchases) of the Fintech Account selected from [tenantId], [accountId], [accountType] and [ownerId].
     * The array in [completion] will have a maximum length based on the [limit] parameter (default 5, max 100).
     *  [token] Fintech Platform API token got from "Create User token" request.
     */
    fun transactions(token: String,
                     ownerId: String,
                     accountId: String,
                     accountType: String,
                     tenantId: String,
                     limit: Int?,
                     offset: Int?,
                     completion: (List<TransactionResponse>?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(AccountType.valueOf(accountType))}/$ownerId/accounts/$accountId/transactionsDetailed")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("limit", limit ?: 0)
            params.put("offset", offset ?: 10)
            val rurl = netHelper.getUrlDataString(url, params)

            request = requestProvider.jsonArrayRequest(Request.Method.GET,
                    rurl, null, netHelper.authorizationToken(token),
                    { response ->
                        try {
                            transactionsResponse(response, accountId, completion)
                        } catch (e: JSONException) {
                            completion(null, netHelper.ReplyParamsUnexpected(e))
                        }
                    }) { error ->
                val status = if (error.networkResponse != null)
                    error.networkResponse.statusCode else -1
                when (status) {
                    409 -> {
                        completion(null, netHelper.IdempotencyError(error))
                    }

                    401 -> {
                        completion(null, netHelper.TokenError(error))
                    }
                    else -> completion(null, netHelper.GenericCommunicationError(error))
                }
            }

            request.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "transactions error", e)
            request = null
        }

        return request
    }

    @Throws(JSONException::class)
    private fun transactionsResponse(response: JSONArray, accountId: String, completion: (List<TransactionResponse>?, Exception?) -> Unit){
        val transactions = ArrayList<TransactionResponse>()
        for (i in 0 until response.length()) {
            val jo = response.getJSONObject(i)
            val joCredited = jo.optJSONObject("credited")
            val joDebited = jo.optJSONObject("debited")
            val joAmount = jo.getJSONObject("amount")
            val joError = jo.optJSONObject("error")

            var errorMessage : String? = null

            joError?.let {
                it.getString("code")
                errorMessage = it.getString("message")
                completion(null, netHelper.GenericCommunicationError(Exception(errorMessage)))
            }

            val creditedFullName = joCredited?.run { this.optString("name") + " " + this.optString("surname") }

            val debitedFullName = joDebited?.run{ this.optString("name") + " " + this.optString("surname") }

            val tf = TransactionResponse(
                    jo.optString("transactionId"),
                    accountId,
                    jo.optString("status"),
                    jo.getString("transactionType"),
                    jo.getString("created"),
                    joCredited?.optString("ownerId"),
                    joDebited?.optString("ownerId"),
                    joAmount.optLong("amount"),
                    joAmount.optLong("amount"),
                    creditedFullName,
                    debitedFullName,
                    joCredited?.optString("picture"),
                    joDebited?.optString("picture"),
                    jo.optString("message"),
                    errorMessage)

            transactions.add(tf)
            log.debug(TAG, jo.optString("transactionId"))
        }

        completion(transactions, null)
    }
}