package com.dwaplatform.android.payout.api

import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.log.Log
import org.json.JSONObject
import javax.inject.Inject

class PayOutAPI @Inject constructor(internal val hostName: String,
                                    internal val queue: IRequestQueue,
                                    internal val requestProvider: IRequestProvider,
                                    internal val log: Log,
                                    val netHelper: NetHelper) {

    private val TAG = "PayOutAPI"

    fun payOut(token: String,
               userid: String,
               bankAccountid: String,
               amount: Long,
               idempotency: String,
               completion: (Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/1.0/fin/payout")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            jsonObject.put("userid", userid)
            jsonObject.put("ibanid", bankAccountid)
            jsonObject.put("amount", amount)
            jsonObject.put("idempotency", idempotency)

            // Request a string response from the provided URL.
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