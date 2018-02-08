package com.fintechplatform.android.payout.api

import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.log.Log
import org.json.JSONObject
import javax.inject.Inject

class PayOutAPI @Inject constructor(internal val hostName: String,
                                    internal val queue: IRequestQueue,
                                    internal val requestProvider: IRequestProvider,
                                    internal val log: Log,
                                    val netHelper: NetHelper) {

    private val TAG = "PayOutAPI"

    fun payOut(token: String,
               userId: String,
               accountId: String,
               tenantId: String,
               linkedBankId: String,
               amount: Long,
               idempotency: String,
               completion: (Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/personal/$userId/accounts/$accountId/linkedBanks/$linkedBankId/cashOuts")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()

            jsonObject.put("amount", amount)

            // FIXME cashout doesnt needs fee
            jsonObject.put("fee", 0L)
            //jsonObject.put("idempotency", idempotency)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.authorizationToken(token), { response ->
                completion(null)
            }) { error ->


                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 -> {
                        completion(netHelper.TokenError(error))
                    }
                    409 -> {
                        completion(netHelper.IdempotencyError(error))
                    }
                    else -> completion(netHelper.GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "payOut", e)
            request = null
        }

        return request
    }
}