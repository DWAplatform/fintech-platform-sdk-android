package com.dwaplatform.android.account.balance.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.card.helpers.JSONHelper
import com.dwaplatform.android.log.Log
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.HashMap

/**
 * Created by ingrid on 07/12/17.
 */
class BalanceAPI constructor(
        internal val hostName: String,
        internal val token: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val jsonHelper: JSONHelper,
        internal val log: Log) {

    private val PROTOCOL_CHARSET = "utf-8"
    private final val TAG = "BalanceAPI"

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


    @Throws(UnsupportedEncodingException::class)
    private fun getUrlDataString(url: String, params: HashMap<String, Any>): String {

        val result = StringBuilder()
        var first = true
        result.append(url)
        for ((key, value) in params) {
            if (first) {
                result.append("?")
                first = false
            } else
                result.append("&")

            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value.toString(), "UTF-8"))
        }

        return result.toString()
    }

    inner class ReplyParamsUnexpected(throwable: Throwable) : Exception(throwable)

    fun balance(userid: String, accountId: String, completion: (Long?, Exception?) -> Unit): IRequest<*>? {

        val url = getURL("/rest/1.0/fin/user/balance")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", userid)

            val rurl = getUrlDataString(url, params)

            val r = requestProvider.jsonObjectRequest(Request.Method.GET, rurl,
                    null, authorizationToken(token), { response ->
                try {
                    //creditcardid
                    val balance = response.optLong("balance")
                    completion(balance, null)
                } catch (e: JSONException) {
                    completion(null, ReplyParamsUnexpected(e))
                }
            }
            ) { error ->
                completion(null, error)
            }
            r.setIRetryPolicy(defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "balance", e)
            request = null
        }

        return request

    }


}