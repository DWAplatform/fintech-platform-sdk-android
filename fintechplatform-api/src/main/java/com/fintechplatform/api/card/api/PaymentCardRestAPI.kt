package com.fintechplatform.api.card.api

import com.android.volley.Request
import com.fintechplatform.api.account.models.AccountType
import com.fintechplatform.api.card.helpers.DateTimeConversion
import com.fintechplatform.api.card.models.PaymentCard
import com.fintechplatform.api.card.models.PaymentCardIssuer
import com.fintechplatform.api.card.models.PaymentCardStatus
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.money.Currency
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class PaymentCardRestAPI constructor(internal val hostName: String,
                                     internal val queue: IRequestQueue,
                                     internal val requestProvider: IRequestProvider,
                                     internal val log: Log,
                                     internal val sandbox: Boolean,
                                     val netHelper: NetHelper) {
    private val TAG = "PaymentCardRestAPI"


    internal data class CardToRegister(val cardNumber: String,
                              val expiration: String,
                              val cvx: String)


    internal data class CardRegistration(val cardRegistrationId: String,
                                val url: String,
                                val preregistrationData: String,
                                val accessKey: String,
                                val registration: String? = null)

    internal data class CreateCardRegistrationReply(val cardId: String,
                                           val cardRegistration: CardRegistration)

    /**
     * Represent the error send as reply from FintechPlatformAPI API.
     *
     * [json] error as json array, can be null in case of json parsing error
     * [throwable] error returned from the underlying HTTP library
     */

    private fun toPaymentCardItem(reply: JSONObject): PaymentCard {

        val issuer = reply.optString("issuer").let {
            PaymentCardIssuer.valueOf(it)
        }
        val created = reply.optString("created").let {
            DateTimeConversion.convertFromRFC3339(it)
        }
        val updated = reply.optString("updated").let {
            DateTimeConversion.convertFromRFC3339(it)
        }
        val currency = reply.optString("currency")?.let {
            Currency.valueOf(it)
        }

        val status = reply.optString("status")?.let {
            PaymentCardStatus.valueOf(it)
        }

        return PaymentCard(
                reply.optString("cardId"),
                reply.optString("alias"),
                reply.optString("expiration"),
                currency,
                issuer,
                status,
                reply.optBoolean("defaultCard"),
                created, updated)

    }

    /**
     * Create a new registration card request, to obtain data useful to send to the card tokenizer service
     *
     * [token] dwaplatform token as get from create card post request
     * [alias] card number alias
     * [expiration] card expiration
     * [callback] callback containing card registration object
     */
    internal fun createCreditCardRegistration(userId: String,
                                     accountId: String,
                                     accountType: AccountType,
                                     tenantId: String, token: String,
                                     numberalias: String,
                                     expiration: String,
                                     currency: Currency,
                                     callback: (CreateCardRegistrationReply?, Exception?) -> Unit): IRequest<*>? {

        log.debug("FintechPlatformAPI", "createCreditCardRegistration")
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
                        log.debug("FintechPlatformAPI", "on response createCreditCardRegistration")
                        try {
                            // linkedCard
                            val cardid = response.getString("cardId")

                            val tokenServiceProvider = response.getString("tspPayload")


                            val mapper = JSONObject(tokenServiceProvider)
                            val crurl = mapper.getString("url")
                            val preregistrationData =
                                    mapper.getString("preregistrationData")
                            val accessKey = mapper.getString("accessKey")
                            val cardRegistrationId = mapper.getString("cardRegistrationId")

                            val c = CardRegistration(cardRegistrationId,
                                    crurl,
                                    preregistrationData,
                                    accessKey,
                                    null)
                            val reply = CreateCardRegistrationReply(cardid, c)

                            callback(reply, null)
                        } catch (e: JSONException) {
                            callback(null, netHelper.ReplyParamsUnexpected(e))
                            log.error(TAG, "createCreditCardRegistration error", e)
                        }
                    }) { error ->
                            callback(null, netHelper.createRequestError(error))
                    }

            request.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "createCreditCardRegistration error", e)
            request = null
        }

        return request

    }

    /**
     * Send card registration to card tokenizer service
     *
     * actual card data to tokenize, identified from [cardnumber], [expiration] and [cvx].
     * [cardRegistration] card registration data to authorize the tokenization
     * [completion] callback containing registration key
     */
    internal fun postCardRegistrationData(userId: String,
                                 accountId: String,
                                 accountType: AccountType,
                                 tenantId: String,
                                 token: String,
                                 cardnumber: String,
                                 expiration: String,
                                 cvx: String,
                                 cardRegistrationReply: CreateCardRegistrationReply,
                                 completion: (PaymentCard?, Exception?) -> Unit)
            : IRequest<*> {

        log.debug("FintechPlatformAPI", "getCardRegistrationData")
        val url = cardRegistrationReply.cardRegistration.url

        val params = HashMap<String, String>()
        params.put("data", cardRegistrationReply.cardRegistration.preregistrationData)
        params.put("accessKeyRef", cardRegistrationReply.cardRegistration.accessKey)
        params.put("cardNumber", cardnumber)
        params.put("cardExpirationDate", expiration)
        params.put("cardCvx", cvx)

        val header = HashMap<String, String>()
        header.put("Content-Type", "application/x-www-form-urlencoded")

        val request = requestProvider.stringRequest(Request.Method.POST, url, params, header,
                { response ->
                    log.debug("FintechPlatformAPI", "on response getCardRegistrationData")
                    sendCardResponseString(userId, accountId, accountType, tenantId, token, response, cardRegistrationReply, completion)
                }) { error ->
                    completion(null, netHelper.createRequestError(error))
                }

        request.setIRetryPolicy(netHelper.defaultpolicy)
        queue.add(request)
        return request
    }

    /**
     * Complete card registration process.
     * [token] dwaplatform token as get from create card post request
     * [cardRegistrationId] univoke id obtained from card registration process
     * [registration] registration key obtained from tokenizer service
     * [completion] callback containing the PaymentCard object
     */
    internal fun sendCardResponseString(userId: String,
                               accountId: String,
                               accountType: AccountType,
                               tenantId: String,
                               token: String,
                               regresponse: String,
                               cardRegistration: CreateCardRegistrationReply,
                               completion: (PaymentCard?, Exception?) -> Unit)
            : IRequest<*>? {
        log.debug("FintechPlatformAPI", "sendCardResponseString")
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userId/accounts/$accountId/linkedCards/${cardRegistration.cardId}")

        var request: IRequest<*>?
        try {
            val payload = cardRegistration.cardRegistration.copy(registration = regresponse)

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
                        try {
                            val c = toPaymentCardItem(response)
                            completion(c, null)
                        } catch (e: JSONException) {
                            completion(null, netHelper.ReplyParamsUnexpected(e))
                        }
                    }) { error ->
                        completion(null, netHelper.createRequestError(error))
                    }

            request.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "sendCardResponseString error", e)
            request = null
        }

        return request

    }

    /**
     * Get Test PaymentCard data to use on sandbox environment.
     * If not in sandbox, will be returned the card data get from cardFrom parameter.
     *
     * [cardFrom] original card data, to use only in production environment
     * [completionHandler] callback containing the card data to use for registration.
     */
    internal fun getCardSafe(cardFrom: CardToRegister,
                         userId: String,
                         accountId: String,
                         accountType: AccountType,
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



    /**
     * Get Payment Cards linked to Fintech Platform Account, identified from [tenantId] [ownerId] [accountType] and [accountId] params.
     *  [token] Fintech Platform API token got from "Create User token" request.
     *  [completion] callback returns the list of cards or an Exception if occurent some errors
     */
    internal fun getPaymentCards(token:String,
                        userId: String,
                        accountId: String,
                        accountType: AccountType,
                        tenantId: String,
                        completion: (List<PaymentCard>?, Exception?) -> Unit): IRequest<*>? {

        val baseurl = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userId/accounts/$accountId/linkedCards")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", userId)
            val url = netHelper.getUrlDataString(baseurl, params)


            val r = requestProvider.jsonArrayRequest(Request.Method.GET, baseurl,
                    null, netHelper.authorizationToken(token),
                    { response: JSONArray ->

                        val creditcards = IntArray(response.length()) {i -> i}.map { i ->
                            val reply = response.getJSONObject(i)
                            toPaymentCardItem(reply)
                        }

                        completion(creditcards, null)
                    })
            { error ->
                completion(null, netHelper.createRequestError(error))
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "get payment cards", e)
            request = null
        }

        return request
    }

    internal fun deletePaymentCard(token:String,
                          userId: String,
                          accountId: String,
                          accountType: AccountType,
                          tenantId: String,
                          cardId: String,
                          completion: (Exception?) -> Unit): IRequest<*>? {

        val baseurl = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userId/accounts/$accountId/linkedCards/$cardId")

        var request: IRequest<*>?
        try {

            val r = requestProvider.nothingRequest(Request.Method.DELETE, baseurl,
                     netHelper.authorizationToken(token),
                    { paymentCardResponse ->

                        completion(null)
                    },
                    { error ->
                        completion(netHelper.createRequestError(error))
                    })
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "delete payment card", e)
            request = null
        }

        return request
    }

    internal fun setDefaultCard(token:String,
                       userId: String,
                       accountId: String,
                       accountType: AccountType,
                       tenantId: String,
                       cardId: String,
                       completion: (PaymentCard?, Exception?) -> Unit): IRequest<*>? {

        val baseurl = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$userId/accounts/$accountId/linkedCards/$cardId/default")

        var request: IRequest<*>?
        try {

            val r = requestProvider.jsonObjectRequest(Request.Method.PUT, baseurl,
                    null, netHelper.authorizationToken(token),
                    { paymentCardResponse ->

                        completion(toPaymentCardItem(paymentCardResponse), null)
                    })
            { error ->
                completion(null, netHelper.createRequestError(error))
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "set default payment card", e)
            request = null
        }

        return request
    }
}