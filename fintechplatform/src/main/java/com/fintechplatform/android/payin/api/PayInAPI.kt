package com.fintechplatform.android.payin.api

import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.log.Log
import com.fintechplatform.android.money.Money
import com.fintechplatform.android.payin.models.PayInReply
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
                   tenantId: String,
                   cardId: String,
                   amount: Money,
                   idempotency: String,
                   completion: (PayInReply?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/personal/$userId/accounts/$accountId/linkedCards/$cardId/cashIns")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()

            val joAmount = JSONObject()
            joAmount.put("amount", amount.value )
            joAmount.put("currency", amount.currency)

            // FIXME fix fee model
            val joFee = JSONObject()
            joFee.put("amount", 0L)
            joFee.put("currency", "EUR")

            jsonObject.put("fee", joFee)
            jsonObject.put("amount", joAmount)
            jsonObject.put("idempotency", idempotency)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.authorizationToken(token), { response ->

                val transactionid = response.getString("transactionId")
                val securecodeneeded = response.getBoolean("secure3D")
                val redirecturl = response.optString("redirectURL")

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