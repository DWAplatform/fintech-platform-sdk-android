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

    fun createLinkedBank(token: String,
                         userid: String,
                         accountId: String,
                         accountType: String,
                         tenantId: String,
                         iban: String,
                         idempotency: String,
                         completion: (BankAccount?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userid/accounts/$accountId/linkedBanks")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            //jsonObject.put("bic", "")
            jsonObject.put("iban", iban)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), { response ->

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
                    else -> {
                        log.error(TAG, "createLinkedBank", error.fillInStackTrace())
                        completion(null, netHelper.GenericCommunicationError(error))
                    }
                }
            }

            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "createLinkedBank", e)
            request = null
        }

        return request
    }

    fun getLinkedBanks(token: String,
                       ownerId: String,
                       accountId: String,
                       accountType: String,
                       tenantId: String,
                       completion: (List<BankAccount>?, Exception?) -> Unit): IRequest<*>? {

        val baseurl = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$ownerId/accounts/$accountId/linkedBanks")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", ownerId)
            val url = netHelper.getUrlDataString(baseurl, params)

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
            log.error(TAG, "getLinkedBanks", e)
            request = null
        }

        return request
    }

    fun getIBAN(token: String,
                ownerId: String,
                accountId: String,
                accountType: String,
                tenantId: String,
                completion: (String?, Exception?) -> Unit): IRequest<*>? {
        val baseurl = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$ownerId/accounts/$accountId/iban")

        var request: IRequest<*>?
        try {
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, baseurl,
                    null, netHelper.authorizationToken(token),
                    { response ->

                        val iban = response.getString("iban")

                        completion(iban, null)
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
            log.error(TAG, "getIban", e)
            request = null
        }

        return request
    }
}