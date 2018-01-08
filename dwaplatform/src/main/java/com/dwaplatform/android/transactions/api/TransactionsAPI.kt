package com.dwaplatform.android.transactions.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.log.Log
import com.dwaplatform.android.transactions.models.TransactionResponse
import org.json.JSONArray
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

class TransactionsAPI @Inject constructor(
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log) {

    private val PROTOCOL_CHARSET = "utf-8"
    private val TAG = "TransactionsAPI"

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

    inner class GenericCommunicationError(throwable: Throwable) : Exception(throwable)

    inner class ReplyParamsUnexpected(throwable: Throwable) : Exception(throwable)

    fun transactions(token: String,
                     userid: String, limit: Int?,
                     offset: Int?,
                     completion: (List<TransactionResponse>?, Exception?) -> Unit): IRequest<*>? {

        val url = getURL("/rest/1.0/fin/user/transactions")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", userid)
            params.put("limit", limit ?: 0)
            params.put("offset", offset ?: 10)
            val rurl = getUrlDataString(url, params)

            request = requestProvider.jsonArrayRequest(Request.Method.GET,
                    rurl, null, authorizationToken(token),
                    { response ->
                        try {
                            val l = transactionsResponse(response)
                            completion(l, null)
                        } catch (e: JSONException) {
                            completion(null, ReplyParamsUnexpected(e))
                        }
                    }) { error ->
                completion(null, GenericCommunicationError(error)) }

            request.setIRetryPolicy(defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "transactions error", e)
            request = null
        }

        return request
    }

    @Throws(JSONException::class)
    private fun transactionsResponse(response: JSONArray): List<TransactionResponse> {
        val transactions = ArrayList<TransactionResponse>()
        for (i in 0..response.length() - 1) {
            val jo = response.getJSONObject(i)

            val tf = TransactionResponse(
                    jo.getString("id"),
                    jo.optString("transactionids"),
                    jo.getString("status"),
                    jo.getString("operationtype"),
                    jo.getLong("creationdate"),
                    jo.optString("resultcode"),
                    jo.optString("crediteduserid"),
                    jo.optString("debiteduserid"),
                    jo.optString("brokerid"),
                    jo.optInt("creditedfunds"),
                    jo.optInt("debitedfunds"),
                    jo.optInt("dwafunds"),
                    jo.optString("crediteduserfullname"),
                    jo.optString("debiteduserfullname"),
                    jo.optString("usermessage"),
                    jo.optInt("brokerfunds"),
                    jo.optString("brokerlegaluserid"),
                    jo.optString("clientname"))

            if (tf.operationtype == "Payout" && tf.status == "CREATED") {
                transactions.add(tf.copy(status = "SUCCEEDED", resultcode = "000000"))
            } else {
                transactions.add(tf)
            }

        }
        return transactions
    }
}