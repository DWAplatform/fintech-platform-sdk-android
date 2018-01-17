package com.dwaplatform.android.transactions.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.log.Log
import com.dwaplatform.android.transactions.models.TransactionResponse
import org.json.JSONArray
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject

class TransactionsAPI @Inject constructor(
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log,
        val netHelper: NetHelper ) {

    private val TAG = "TransactionsAPI"

    fun transactions(token: String,
                     userid: String, limit: Int?,
                     offset: Int?,
                     completion: (List<TransactionResponse>?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/1.0/fin/user/transactions")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", userid)
            params.put("limit", limit ?: 0)
            params.put("offset", offset ?: 10)
            val rurl = netHelper.getUrlDataString(url, params)

            request = requestProvider.jsonArrayRequest(Request.Method.GET,
                    rurl, null, netHelper.authorizationToken(token),
                    { response ->
                        try {
                            val l = transactionsResponse(response)
                            completion(l, null)
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
    private fun transactionsResponse(response: JSONArray): List<TransactionResponse> {
        val transactions = ArrayList<TransactionResponse>()
        for (i in 0..response.length() - 1) {
            val jo = response.getJSONObject(i)

            val tf = TransactionResponse(
                    jo.getString("id"),
                    jo.optString("transactionids"),
                    jo.getString("status"),
                    jo.getString("operationtype"),
                    jo.getLong("creationdate"),
                    jo.optString("resultcode"),
                    jo.optString("crediteduserid"),
                    jo.optString("debiteduserid"),
                    jo.optString("brokerid"),
                    jo.optInt("creditedfunds"),
                    jo.optInt("debitedfunds"),
                    jo.optInt("dwafunds"),
                    jo.optString("crediteduserfullname"),
                    jo.optString("debiteduserfullname"),
                    jo.optString("usermessage"),
                    jo.optInt("brokerfunds"),
                    jo.optString("brokerlegaluserid"),
                    jo.optString("clientname"))

            if (tf.operationtype == "Payout" && tf.status == "CREATED") {
                transactions.add(tf.copy(status = "SUCCEEDED", resultcode = "000000"))
            } else {
                transactions.add(tf)
            }

        }
        return transactions
    }
}