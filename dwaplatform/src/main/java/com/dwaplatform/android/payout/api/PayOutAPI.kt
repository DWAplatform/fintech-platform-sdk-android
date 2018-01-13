package com.dwaplatform.android.payout.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.log.Log
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

/**
 * Created by ingrid on 04/01/18.
 */
class PayOutAPI @Inject constructor(internal val hostName: String,
                                    internal val queue: IRequestQueue,
                                    internal val requestProvider: IRequestProvider,
                                    internal val log: Log) {

    inner class GenericCommunicationError(throwable: Throwable) : Exception(throwable)

    inner class IdempotencyError(throwable: Throwable) : Exception(throwable)

    private val TAG = "PayOutAPI"

    private val PROTOCOL_CHARSET = "utf-8"

    private fun getURL(path: String): String {
        if(hostName.startsWith("http://") || hostName.startsWith("https://")){
            return "$hostName$path"
        } else {
            return "https://$hostName$path"
        }
    }

    private val defaultpolicy = DefaultRetryPolicy(
            30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

    private fun authorizationToken(token: String): Map<String, String> {
        val header = HashMap<String, String>()
        header.put("Authorization", "Bearer $token")
        return header
    }

    fun payOut(token: String,
               userid: String,
               bankAccountid: String,
               amount: Long,
               idempotency: String,
               completion: (Exception?) -> Unit): IRequest<*>? {
        val url = getURL("/rest/1.0/fin/payout")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            jsonObject.put("userid", userid)
            jsonObject.put("ibanid", bankAccountid)
            jsonObject.put("amount", amount)
            jsonObject.put("idempotency", idempotency)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    authorizationToken(token), { response ->
                completion(null)
            }) { error ->


                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    409 -> {
                        completion(IdempotencyError(error))
                    }
                    else -> completion(GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "payOut", e)
            request = null
        }

        return request
    }
}