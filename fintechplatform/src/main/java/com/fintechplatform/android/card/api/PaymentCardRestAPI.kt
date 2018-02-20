package com.fintechplatform.android.card.api

import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.card.helpers.JSONHelper
import com.fintechplatform.android.card.models.PaymentCardItem
import com.fintechplatform.android.log.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class PaymentCardRestAPI constructor(internal val hostName: String,
                                     internal val queue: IRequestQueue,
                                     internal val requestProvider: IRequestProvider,
                                     internal val log: Log,
                                     internal val jsonHelper: JSONHelper,
                                     internal val sandbox: Boolean,
                                     val netHelper: NetHelper) {
    private val TAG = "PaymentCardRestAPI"

    data class CardToRegister(val cardNumber: String,
                              val expiration: String,
                              val cvx: String)


    data class CardRegistration(val cardRegistrationId: String,
                                val url: String,
                                val preregistrationData: String,
                                val accessKey: String,
                                val registration: String? = null)

    /**
     * Represent the error send as reply from FintechPlatform API.
     *
     * @property json error as json array, can be null in case of json parsing error
     * @property throwable error returned from the underlying HTTP library
     */
    data class APIReplyError(val json: JSONArray?, val throwable: Throwable) : java.lang.Exception(throwable)

    /**
     * Create a new registration card request, to obtain data useful to send to the card tokenizer service
     *
     * @param token dwaplatform token as get from create card post request
     * @param alias card number alias
     * @param expiration card expiration
     * @param completionHandler callback containing card registration object
     */
    fun createCreditCardRegistration(userId: String,
                                     accountId: String,
                                     accountType: String,
                                     tenantId: String, token: String,
                                     numberalias: String,
                                     expiration: String,
                                     currency: String,
                                     callback: (CardRegistration?, Exception?) -> Unit): IRequest<*>? {

        log.debug("FintechPlatform", "createCreditCardRegistration")
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userId/accounts/$accountId/linkedCards")

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
                            val preregistrationData =
                                    mapper.getString("preregistrationData")
                            val accessKey = mapper.getString("accessKey")

                            val c = CardRegistration(cardid,
                                    crurl,
                                    preregistrationData,
                                    accessKey,
                                    null)

                            callback(c, null)
                        } catch (e: JSONException) {
                            callback(null, netHelper.ReplyParamsUnexpected(e))
                            log.error(TAG, "transactions error", e)
                        }
                    }) { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 -> {
                        callback(null, netHelper.TokenError(error))
                        log.error(TAG, "transactions error", error.fillInStackTrace())
                    }
                    else -> {
                        callback(null, netHelper.createRequestError(error))
                        log.error(TAG, "transactions error", error.fillInStackTrace())
                    }
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

    /**
     * Send card registration to card tokenizer service
     *
     * @param card actual card data to tokenize
     * @param cardRegistration card registration data to authorize the tokenization
     * @param completionHandler callback containing registration key
     */
    fun postCardRegistrationData(userId: String,
                                accountId: String,
                                 accountType: String,
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
                    sendCardResponseString(userId, accountId, accountType, tenantId, token, response, cardRegistration, completion)
                }) { error ->
            val status = if (error.networkResponse != null) error.networkResponse.statusCode
            else -1
            when (status) {
                401 -> {
                    completion(null, netHelper.TokenError(error))
                    log.error(TAG, "transactions error", error.fillInStackTrace())
                }
                else -> {
                    completion(null, netHelper.createRequestError(error))
                    log.error(TAG, "transactions error", error.fillInStackTrace())
                }
            }
        }

        request.setIRetryPolicy(netHelper.defaultpolicy)
        queue.add(request)
        return request
    }

    /**
     * Complete card registration process.
     * @param token dwaplatform token as get from create card post request
     * @param cardRegistrationId univoke id obtained from card registration process
     * @param registration registration key obtained from tokenizer service
     * @param completionHandler callback containing the PaymentCard object
     */
    fun sendCardResponseString(userId: String,
                                       accountId: String,
                               accountType: String,
                                       tenantId: String,
                                       token: String,
                                       regresponse: String,
                                       cardRegistration: CardRegistration,
                                       completion: (PaymentCardItem?, Exception?) -> Unit)
            : IRequest<*>? {
        log.debug("FintechPlatform", "sendCardResponseString")
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userId/accounts/$accountId/linkedCards/${cardRegistration.cardRegistrationId}")

        var request: IRequest<*>?
        try {
            val payload = cardRegistration.copy(registration = regresponse)

            val joPayload = JSONObject()
            joPayload.put("accessKey", payload.accessKey)
            joPayload.put("cardRegistrationId", payload.cardRegistrationId)
            joPayload.put("preregistrationData", payload.preregistrationData)
            joPayload.put("registration", payload.registration)
            joPayload.put("url", payload.url)

            val jo = JSONObject()
            jo.put("tspPayload", joPayload.toString())

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
                    401 -> {
                        completion(null, netHelper.TokenError(error))
                        log.error(TAG, "transactions error", error.fillInStackTrace())
                    }
                    else -> {
                        completion(null, netHelper.createRequestError(error))
                        log.error(TAG, "transactions error", error.fillInStackTrace())
                    }
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

    /**
     * Get Test PaymentCard data to use on sandbox environment.
     * If not in sandbox, will be returned the card data get from cardFrom parameter.
     *
     * @param cardFrom original card data, to use only in production environment
     * @param completionHandler callback containing the card data to use for registration.
     */
    open fun getCardSafe(cardFrom: CardToRegister,
                         userId: String,
                         accountId: String,
                         accountType: String,
                         tenantId: String,
                         token: String,
                         completionHandler: (CardToRegister?, Exception?) -> Unit) {

        if (!sandbox) {
            completionHandler(CardToRegister(cardFrom.cardNumber, cardFrom.expiration, cardFrom.cvx), null)
        } else {

            val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userId/accounts/$accountId/linkedCardsTestCards")

            val request = requestProvider.jsonArrayRequest(Request.Method.GET, url, null,
                    netHelper.authorizationToken(token),
                    { response ->
                        try {
                            val jo = response.getJSONObject(0)
                            val cardToRegister = CardToRegister(jo.optString("cardNumber"),
                                    jo.optString("expiration"),
                                    jo.optString("cxv"))

                            completionHandler(cardToRegister, null)
                        } catch (e: JSONException) {
                            completionHandler(null, PaymentCardAPI.ParseReplyParamsException(e))
                        }
                    }) { error ->
                completionHandler(null, netHelper.createRequestError(error))
            }

            request.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(request)
        }
    }

    fun getPaymentCards(token:String,
                        userId: String,
                        accountId: String,
                        accountType: String,
                        tenantId: String,
                        completion: (List<PaymentCardItem>?, Exception?) -> Unit): IRequest<*>? {

        val baseurl = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userId/accounts/$accountId/linkedCards")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", userId)
            val url = netHelper.getUrlDataString(baseurl, params)


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
                    401 -> {
                        completion(null, netHelper.TokenError(error))
                        log.error(TAG, "transactions error", error.fillInStackTrace())
                    }
                    else -> {
                        completion(null, netHelper.createRequestError(error))
                        log.error(TAG, "transactions error", error.fillInStackTrace())
                    }
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