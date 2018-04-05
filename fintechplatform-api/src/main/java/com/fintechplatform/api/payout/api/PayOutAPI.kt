package com.fintechplatform.api.payout.api

import com.android.volley.Request
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
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
               accountType: String,
               tenantId: String,
               linkedBankId: String,
               amount: Long,
               idempotency: String,
               completion: (Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userId/accounts/$accountId/linkedBanks/$linkedBankId/cashOuts")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            val joAmount = JSONObject()
            joAmount.put("amount", amount)
            joAmount.put("currency", "EUR")
            jsonObject.put("amount", joAmount)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), { response ->
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