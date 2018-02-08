package com.fintechplatform.android.iban.api

import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.iban.models.BankAccount
import com.fintechplatform.android.log.Log
import org.json.JSONArray
import org.json.JSONObject
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
                   accountId: String,
                   tenantId: String,
                   iban: String,
                   completion: (BankAccount?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/personal/$userid/accounts/$accountId/linkedBanks")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            //jsonObject.put("bic", "")
            jsonObject.put("iban", iban)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.authorizationToken(token), { response ->

                val ibanid = response.getString("bankId")
                val iban = response.getString("iban")
                val activestate = response.optString("status")

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
                        accountId: String,
                        tenantId: String,
                        completion: (List<BankAccount>?, Exception?) -> Unit): IRequest<*>? {

        val baseurl = netHelper.getURL("/rest/v1/account/tenants/$tenantId/personal/$userid/accounts/$accountId/linkedBanks")

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
                                    reply.optString("bankId"),
                                    reply.optString("iban"),
                                    reply.optString("status"))
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