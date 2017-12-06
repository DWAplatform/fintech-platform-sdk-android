package com.dwaplatform.android.payin.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.dwaplatform.android.account.Account
import com.dwaplatform.android.acquiringchannels.PaymentCard
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.card.helpers.JSONHelper
import com.dwaplatform.android.models.Amount
import com.dwaplatform.android.payin.models.PayInReply
import org.json.JSONObject
import java.util.HashMap

/**
 * Created by ingrid on 06/12/17.
 */
class PayInRestAPI constructor(
        internal val hostName: String,
        internal val token: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val jsonHelper: JSONHelper) {

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

    fun payIn(account: Account, completion: (PayInReply?, Error?) -> Unit) {
        val url = getURL("/rest/1.0/fin/payin")

        var request: IRequest<*>?
        try {
            val amount = account.balance.getBalance()

            val jsonObject = jsonHelper.buildJSONObject()

            jsonObject.put("userid", account.user.id)
            jsonObject.put("creditcardid", account.paymentCard.id)
            jsonObject.put("amount", amount.value)
//            jsonObject.put("idempotency", idempotency)

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
 //                       completion(null, IdempotencyError(error))
                    }
 //                   else -> completion(null, GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
 //           log.error(TAG, "payIn", e)
            request = null
        }

//        return request
    }
}