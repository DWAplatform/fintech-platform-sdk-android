package com.fintechplatform.api.iban.api

import com.android.volley.Request
import com.fintechplatform.api.account.models.AccountType
import com.fintechplatform.api.iban.models.BankAccount
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

/**
 * Iban API class for communication with Fintech Platform to handle Bank Accounts.
 */
class IbanAPI @Inject constructor(internal val hostName: String,
                                  internal val queue: IRequestQueue,
                                  internal val requestProvider: IRequestProvider,
                                  val netHelper: NetHelper

){
    private val TAG = "IbanAPI"

    /**
     * Link a new Bank Account with [iban] to the Fintech User Account identified from: [tenantId] [ownerId] [accountType] [accountId].
     * Use [token] got from "Create User token" request.
     * Use [idempotency] parameter to avoid multiple inserts.
     * [completion] callback will be call with BankAccount object in case of success or Exception in case of errors.
     */
    fun createLinkedBank(token: String,
                         ownerId: String,
                         accountId: String,
                         accountType: String,
                         tenantId: String,
                         iban: String,
                         idempotency: String,
                         completion: (BankAccount?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(AccountType.valueOf(accountType))}/$ownerId/accounts/$accountId/linkedBanks")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            //jsonObject.put("bic", "") not currenyl used.
            jsonObject.put("iban", iban)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), { response ->

                val ibanid = response.getString("bankId")
                val iban = response.getString("iban")
                val accountId = response.getString("accountId")
                val activestate = response.optString("status")

                completion(BankAccount(ibanid, accountId, iban, activestate), null)
            }) { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 ->
                        completion(null, netHelper.TokenError(error))
                    else -> {
                        completion(null, netHelper.GenericCommunicationError(error))
                    }
                }
            }

            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
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

        val baseurl = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(AccountType.valueOf(accountType))}/$ownerId/accounts/$accountId/linkedBanks")

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
                                    reply.getString("accountId"),
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
        val baseurl = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(AccountType.valueOf(accountType))}/$ownerId/accounts/$accountId/iban")

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
            request = null
        }

        return request
    }
}