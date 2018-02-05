package com.fintechplatform.android.sample.auth.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.log.Log
import com.fintechplatform.android.sample.auth.keys.CheckPin
import com.fintechplatform.android.sample.auth.keys.CheckPinState
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

/**
 * Created by ingrid on 18/12/17.
 */
class AuthenticationAPI constructor(internal val hostName: String,
                                    internal val queue: IRequestQueue,
                                    internal val requestProvider: IRequestProvider,
                                    internal val log: Log){
    inner class GenericCommunicationError(throwable: Throwable) : Exception(throwable)

    inner class ReplyParamsUnexpected(throwable: Throwable) : Exception(throwable)

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

    fun sendTokenNoReply(userid: String, token: String): IRequest<*>? {
        return sendToken(userid, token) {}
    }

    fun sendToken(userid: String, token: String, completion: (Exception?) -> Unit): IRequest<*>? {
        val url = getURL("/rest/1.0/user/notification/token")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            jsonObject.put("userid", userid)
            jsonObject.put("token", token)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    authorizationToken(token),
                    { completion(null) })
            {error -> completion(error) }
            r.setIRetryPolicy(defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "sendToken", e)
            request = null
        }

        return request
    }

    fun checkpin(userid: String, pin: String,
                 completion: (CheckPin?, Exception?) -> Unit)
            : IRequest<*>? {

        val url = getURL("/rest/1.0/user/checkpin")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", userid)
            params.put("pin", pin)

            val rurl = getUrlDataString(url, params)

            val r = requestProvider.jsonObjectRequest(Request.Method.GET, rurl,
                    null, { response ->
                try {

                    val userid = response.getString("userid")
                    val token = response.getString("token")

                    completion(CheckPin(userid, token, CheckPinState.SUCCESS), null)
                } catch (e: JSONException) {
                    completion(null, ReplyParamsUnexpected(e))
                }
            }
            ) { error ->
                val status = if (error.networkResponse != null)
                    error.networkResponse.statusCode else -1
                when (status) {
                    403 -> {
                        completion(CheckPin(userid, "", CheckPinState.CODE_ERROR),
                                null)
                    }
                    401 -> {
                        completion(CheckPin(userid, "", CheckPinState.LIMIT_REACHED),
                                null)
                    }
                    else -> completion(null, GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "checkpin", e)
            request = null
        }

        return request
    }


    fun userToken(userid: String, tenantId: String, pin: String, completion: (CheckPin?, Exception?) -> Unit): IRequest<*>? {

        val url = getURL("/rest/v1/mobile/tenants/$tenantId/users/$userid/tokens")

        var request: IRequest<*>?
        try {
            val jo = JSONObject()
            jo.put("pin", pin)

            request = requestProvider.jsonObjectRequest(Request.Method.POST, url, jo, { response ->
                try {
                    //val id = response.getString("userId")
                    val token = response.getString("token")

                    completion(CheckPin(userid, token, CheckPinState.SUCCESS), null)
                } catch (e: JSONException) {
                    completion(null, e)
                }
            }) { error ->

                val status = if (error.networkResponse != null)
                    error.networkResponse.statusCode else -1
                when (status) {
                    403 -> {
                        completion(CheckPin(userid, "", CheckPinState.CODE_ERROR),
                                null)
                    }
                    401 -> {
                        completion(CheckPin(userid, "", CheckPinState.LIMIT_REACHED),
                                null)
                    }
                    else -> completion(null, GenericCommunicationError(error))
                }


            }

            request.setIRetryPolicy(defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "token error", e)
            request = null
        }

        return request
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
}