package com.dwaplatform.android.payin.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.dwafintech.dwapay.model.Money
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.log.Log
import com.dwaplatform.android.payin.models.PayInReply
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

/**
 * Created by ingrid on 06/12/17.
 */
open class PayInAPI @Inject constructor(
        internal val hostName: String,
        internal val token: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log) {

    inner class GenericCommunicationError(throwable: Throwable) : Exception(throwable)

    inner class IdempotencyError(throwable: Throwable) : Exception(throwable)

    private final val TAG = "PayInAPI"

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

    fun payIn(userId: String,
              accountId: String,
              creditcardId: String,
              amount: Money,
              idempotency: String,
              completion: (PayInReply?, Exception?) -> Unit): IRequest<*>? {
        val url = getURL("/rest/1.0/fin/payin")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            jsonObject.put("userid", userId)
            jsonObject.put("creditcardid", creditcardId)
            jsonObject.put("amount", amount.value)
            jsonObject.put("idempotency", idempotency)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    authorizationToken(token), { response ->
                val transactionid = response.getString("transactionid")
                val securecodeneeded = response.getBoolean("securecodeneeded")
                val redirecturl = response.optString("redirecturl")

                completion(PayInReply(transactionid, securecodeneeded, redirecturl), null)
            }) { error ->


                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    409 -> {
                        completion(null, IdempotencyError(error))
                    }
                    else -> completion(null, GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "payIn", e)
            request = null
        }

        return request
    }

}