package com.dwaplatform.android.payin.api

import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.log.Log
import com.dwaplatform.android.money.Money
import com.dwaplatform.android.payin.models.PayInReply
import org.json.JSONObject
import javax.inject.Inject

open class PayInAPI @Inject constructor(
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log,
        val netHelper: NetHelper) {

    private val TAG = "PayInAPI"

    open fun payIn(token: String,
                   userId: String,
                   accountId: String,
                   creditcardId: String,
                   amount: Money,
                   idempotency: String,
                   completion: (PayInReply?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/1.0/fin/payin")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            jsonObject.put("userid", userId)
            jsonObject.put("creditcardid", creditcardId)
            jsonObject.put("amount", amount.value)
            jsonObject.put("idempotency", idempotency)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.authorizationToken(token), { response ->
                val transactionid = response.getString("transactionid")
                val securecodeneeded = response.getBoolean("securecodeneeded")
                val redirecturl = response.optString("redirecturl")

                completion(PayInReply(transactionid, securecodeneeded, redirecturl), null)
            }) { error ->


                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 -> {
                        completion(null, netHelper.TokenError(error))
                    }

                    409 -> {
                        completion(null, netHelper.IdempotencyError(error))
                    }
                    else -> completion(null, netHelper.GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "payIn", e)
            request = null
        }

        return request
    }

}