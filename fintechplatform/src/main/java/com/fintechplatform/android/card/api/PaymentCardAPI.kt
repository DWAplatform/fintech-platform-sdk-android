package com.fintechplatform.android.card.api

import android.util.JsonReader
import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.card.helpers.DateTimeConversion
import com.fintechplatform.android.card.models.PaymentCardItem
import com.fintechplatform.android.log.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class PaymentCardAPI constructor(internal val hostName: String,
                                 internal val queue: IRequestQueue,
                                 internal val requestProvider: IRequestProvider,
                                 internal val log: Log,
                                 internal val sandbox: Boolean,
                                 val netHelper: NetHelper) {
    private val TAG = "PaymentCardAPI"

    interface FailureCallback {
        fun onFailure(e: Exception)
    }


    interface CardRegistrationCallback : FailureCallback {
        fun onSuccess(cardRegistration: CardRegistration)
    }

    data class CardToRegister(val cardNumber: String,
                              val expiration: String,
                              val cvx: String)


    data class CardRegistration(val cardRegistrationId: String,
                                val url: String,
                                val preregistrationData: String,
                                val accessKey: String,
                                val tokenCard: String? = null)

    fun createCreditCard(userId: String,
                         accountId: String,
                         tenantId: String,
                         token: String,
                         cnumber: String,
                         exp: String, cvxValue: String, currency: String,
                         completion: (PaymentCardItem?, Exception?) -> Unit): IRequest<*>? {

        log.debug("FintechPlatform", "createCreditCard")

        // TODO use getCardSafe (/rest/client/user/account/card/test)
        val cardnumber: String
        val expiration: String
        val cvx: String
        if (sandbox) {
            cardnumber = "3569990000000157"
            expiration = "1220"
            cvx = "123"
        } else {
            cardnumber = cnumber
            expiration = exp
            cvx = cvxValue
        }

        val numberalias: String
        if (cardnumber.length >= 16) {
            numberalias = cardnumber.substring(0, 6) + "XXXXXX" +
                    cardnumber.substring(cardnumber.length - 4, cardnumber.length)
        } else {
            numberalias = "XXXXXXXXXXXXXXXX"
        }

        val request = createCreditCardRegistration(userId,
                accountId,
                tenantId,
                token, numberalias, expiration, currency,
                object : CardRegistrationCallback {
                    override fun onSuccess(cardRegistration: CardRegistration) {
                        log.debug("FintechPlatform", "on success createCreditCard")

                        getCardRegistrationData(userId,
                                accountId,
                                tenantId,
                                token,
                                cardnumber,
                                expiration,
                                cvx,
                                cardRegistration,
                                completion)

                    }

                    override fun onFailure(e: Exception) {
                        completion(null, e)
                    }
                })

        return request
    }


    // post linked cards
    private fun createCreditCardRegistration(userId: String,
                                             accountId: String,
                                             tenantId: String, token: String, numberalias: String, expiration: String, currency: String,
                                             callback: CardRegistrationCallback): IRequest<*>? {

        log.debug("FintechPlatform", "createCreditCardRegistration")
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/users/$userId/accounts/$accountId/linkedCards")

        var request: IRequest<*>?
        try {
            val jo = JSONObject()
            jo.put("alias", numberalias)
            jo.put("expiration", expiration)
            jo.put("currency", currency)

            request = requestProvider.jsonObjectRequest(Request.Method.POST, url, jo,
                    netHelper.authorizationToken(token),
                    { response ->
                        log.debug("FintechPlatform", "on response createCreditCardRegistration")
                        try {
                            // linkedCard
                            val cardid = response.getString("cardId")

                            val tokenServiceProvider = response.getString("tspPayload")

                            val mapper = JSONObject(tokenServiceProvider)
                            val crurl = mapper.getString("url")

//                            val cardRegistration = mapper.getString("cardRegistrationId")
                            val preregistrationData =
                                    mapper.getString("preregistrationData")
                            val accessKey = mapper.getString("accessKey")

                            val c = CardRegistration(cardid,
                                    crurl,
                                    preregistrationData,
                                    accessKey,
                                    null)

                            callback.onSuccess(c)
                        } catch (e: JSONException) {
                            callback.onFailure(netHelper.ReplyParamsUnexpected(e))
                        }
                    }) { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 ->
                        callback.onFailure(netHelper.TokenError(error))
                    else ->
                    callback.onFailure(netHelper.GenericCommunicationError(error))
                }
            }

            request.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "transactions error", e)
            request = null
        }

        return request

    }

    private fun getCardRegistrationData(userId: String,
                                        accountId: String,
                                        tenantId: String,
                                        token: String,
                                        cardnumber: String,
                                        expiration: String, cvx: String,
                                        cardRegistration: CardRegistration,
                                        completion: (PaymentCardItem?, Exception?) -> Unit)
            : IRequest<*> {

        log.debug("FintechPlatform", "getCardRegistrationData")
        val url = cardRegistration.url

        val params = HashMap<String, String>()
        params.put("data", cardRegistration.preregistrationData)
        params.put("accessKeyRef", cardRegistration.accessKey)
        params.put("cardNumber", cardnumber)
        params.put("cardExpirationDate", expiration)
        params.put("cardCvx", cvx)

        val header = HashMap<String, String>()
        header.put("Content-Type", "application/x-www-form-urlencoded")

        val request = requestProvider.stringRequest(Request.Method.POST, url, params, header,
                { response ->
                    log.debug("FintechPlatform", "on response getCardRegistrationData")
                    sendCardResponseString(userId, accountId, tenantId, token, response, cardRegistration, completion)
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

        request.setIRetryPolicy(netHelper.defaultpolicy)
        queue.add(request)
        return request
    }

    private fun sendCardResponseString(userId: String,
                                       accountId: String,
                                       tenantId: String,
                                       token: String,
                                       regresponse: String,
                                       cardRegistration: CardRegistration,
                                       completion: (PaymentCardItem?, Exception?) -> Unit)
            : IRequest<*>? {
        log.debug("FintechPlatform", "sendCardResponseString")
        val url = netHelper.getURL("/rest/v1/account/tenants/$tenantId/personal/$userId/accounts/$accountId/linkedCards/${cardRegistration.cardRegistrationId}")

        var request: IRequest<*>?
        try {
            val jo = JSONObject()
            jo.put("tspPayload", regresponse)

            request = requestProvider.jsonObjectRequest(Request.Method.PUT, url, jo,
                    netHelper.authorizationToken(token),
                    { response ->
                        log.debug("FintechPlatform", "on response sendCardResponseString")
                        try {

                            val creditcardid = response.getString("cardId")
                            val numberalias = response.getString("alias")
                            val expirationdate = response.getString("expiration")
                            val activestate = response.getString("status")
                            val currency = response.getString("currency")

                            val createOpt: String? = response.optString("created")
//                            val createDate = createOpt?.let { create ->
//                                DateTimeConversion.convertFromRFC3339(create)
//                            }
                            val c = PaymentCardItem(creditcardid, numberalias,
                                    expirationdate, currency, null, activestate, null, createOpt)
                            completion(c, null)
                        } catch (e: JSONException) {
                            completion(null, netHelper.ReplyParamsUnexpected(e))
                        }
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

            request.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "transactions error", e)
            request = null
        }

        return request

    }


    fun getPaymentCards(token:String,
                       userId: String,
                        accountId: String,
                        tenantId: String,
                       completion: (List<PaymentCardItem>?, Exception?) -> Unit): IRequest<*>? {

        val baseurl = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/users/$userId/accounts/$accountId/linkedCards")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", userId)
            val url = netHelper.getUrlDataString(baseurl, params)


            // Request a string response from the provided URL.
            val r = requestProvider.jsonArrayRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token),
                    { response: JSONArray ->

                        val creditcards = IntArray(response.length()) {i -> i}.map { i ->
                            val reply = response.getJSONObject(i)

                            PaymentCardItem(
                                    reply.optString("cardId"),
                                    reply.optString("alias"),
                                    reply.optString("expiration"),
                                    reply.optString("currency"),
                                    null,
                                    reply.optString("status"),
                                    null,
                                    reply.optString("created"))
                        }

                        completion(creditcards, null)
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
            log.error(TAG, "getcreditcards", e)
            request = null
        }

        return request
    }

}