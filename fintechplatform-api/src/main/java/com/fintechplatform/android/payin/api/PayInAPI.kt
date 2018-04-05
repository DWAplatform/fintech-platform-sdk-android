package com.fintechplatform.android.payin.api

import com.android.volley.Request
import com.fintechplatform.android.log.Log
import com.fintechplatform.android.money.Money
import com.fintechplatform.android.net.IRequest
import com.fintechplatform.android.net.IRequestProvider
import com.fintechplatform.android.net.IRequestQueue
import com.fintechplatform.android.net.NetHelper
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
                   accountType: String,
                   tenantId: String,
                   cardId: String,
                   amount: Money,
                   idempotency: String,
                   completion: (PayInReply?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userId/accounts/$accountId/linkedCards/$cardId/cashIns")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()

            val joAmount = JSONObject()
            joAmount.put("amount", amount.value )
            joAmount.put("currency", amount.currency)

            jsonObject.put("amount", joAmount)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), { response ->

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