package com.dwaplatform.android.iban.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.iban.models.BankAccount
import com.dwaplatform.android.log.Log
import com.dwaplatform.android.profile.models.UserProfileReply
import org.json.JSONArray
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject

class IbanAPI @Inject constructor(internal val hostName: String,
                                  internal val queue: IRequestQueue,
                                  internal val requestProvider: IRequestProvider,
                                  internal val log: Log,
                                  val netHelper: NetHelper

){
    private val TAG = "IbanAPI"

    fun createIBAN(token: String,
                   userid: String,
                   iban: String,
                   completion: (BankAccount?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/1.0/fin/iban/create")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            jsonObject.put("userid", userid)
            jsonObject.put("iban", iban)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.authorizationToken(token), { response ->

                val ibanid = response.getString("ibanid")
                val iban = response.getString("iban")
                val activestate = response.getString("activestate")

                completion(BankAccount(ibanid, iban, activestate), null)
            }) { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 ->
                        completion(null, netHelper.TokenError(error))
                    else ->
                        completion(null, netHelper.GenericCommunicationError(error))
                }
            }

            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "createIBAN", e)
            request = null
        }

        return request
    }

    fun getbankAccounts(token: String,
                        userid: String,
                        completion: (List<BankAccount>?, Exception?) -> Unit): IRequest<*>? {

        val baseurl = netHelper.getURL("/rest/1.0/fin/iban/list")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", userid)
            val url = netHelper.getUrlDataString(baseurl, params)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonArrayRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token),
                    { response: JSONArray ->

                        val bas = IntArray(response.length()) {i -> i}.map { i ->
                            val reply = response.getJSONObject(i)

                            BankAccount(
                                    reply.optString("ibanid"),
                                    reply.optString("iban"),
                                    reply.optString("activestate"))
                        }

                        completion(bas, null)
                    })
            {error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 ->
                        completion(null, netHelper.TokenError(error))
                    else ->
                        completion(null, netHelper.GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "getbankAccounts", e)
            request = null
        }

        return request
    }
}