package com.dwaplatform.android.card.api

import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.card.helpers.DateTimeConversion
import com.dwaplatform.android.card.models.PaymentCardItem
import com.dwaplatform.android.log.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * Created by ingrid on 21/12/17.
 */
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
                         token: String,
                         cnumber: String,
                         exp: String, cvxValue: String,
                         completion: (PaymentCardItem?, Exception?) -> Unit): IRequest<*>? {

        log.debug("DWAPAY", "createCreditCard")

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
                token, numberalias, expiration,
                object : CardRegistrationCallback {
                    override fun onSuccess(cardRegistration: CardRegistration) {
                        log.debug("DWAPAY", "on success createCreditCard")

                        getCardRegistrationData(userId,
                                accountId,
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

    private fun createCreditCardRegistration(userId: String,
                                             accountId: String, token: String, numberalias: String, expiration: String,
                                             callback: CardRegistrationCallback): IRequest<*>? {

        log.debug("DWAPAY", "createCreditCardRegistration")
        val url = netHelper.getURL("/rest/v1/" + userId + "/fin/creditcards")

        var request: IRequest<*>?
        try {
            val jo = JSONObject()
            jo.put("numberalias", numberalias)
            jo.put("expiration", expiration)

            request = requestProvider.jsonObjectRequest(Request.Method.POST, url, jo,
                    netHelper.authorizationToken(token),
                    { response ->
                        log.debug("DWAPAY", "on response createCreditCardRegistration")
                        try {
                            //creditcardid
                            val creditcardid = response.getString("id")

                            val cardRegistration = response.getJSONObject("cardRegistration")
                            val preregistrationData =
                                    cardRegistration.getString("preregistrationData")
                            val accessKey = cardRegistration.getString("accessKey")
                            val crurl = cardRegistration.getString("url")

                            val c = CardRegistration(creditcardid,
                                    crurl,
                                    preregistrationData,
                                    accessKey,
                                    null)

                            callback.onSuccess(c)
                        } catch (e: JSONException) {
                            callback.onFailure(netHelper.ReplyParamsUnexpected(e))
                        }
                    }) { error -> callback.onFailure(netHelper.GenericCommunicationError(error)) }

            request!!.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "transactions error", e)
            request = null
        }

        return request

    }

    private fun getCardRegistrationData(userId: String,
                                        accountId: String,
                                        token: String,
                                        cardnumber: String,
                                        expiration: String, cvx: String,
                                        cardRegistration: CardRegistration,
                                        completion: (PaymentCardItem?, Exception?) -> Unit)
            : IRequest<*> {

        log.debug("DWAPAY", "getCardRegistrationData")
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
                    log.debug("DWAPAY", "on response getCardRegistrationData")
                    sendCardResponseString(userId, accountId, token, response, cardRegistration, completion)
                }) { error -> completion(null, netHelper.GenericCommunicationError(error)) }

        request.setIRetryPolicy(netHelper.defaultpolicy)
        queue.add(request)
        return request
    }

    private fun sendCardResponseString(userId: String,
                                       accountId: String,
                                       token: String,
                                       regresponse: String,
                                       cardRegistration: CardRegistration,
                                       completion: (PaymentCardItem?, Exception?) -> Unit)
            : IRequest<*>? {
        log.debug("DWAPAY", "sendCardResponseString")
        val url = netHelper.getURL("/rest/v1/" + userId + "/fin/creditcards/" +
                cardRegistration.cardRegistrationId)

        var request: IRequest<*>?
        try {
            val jo = JSONObject()
            jo.put("registration", regresponse)

            request = requestProvider.jsonObjectRequest(Request.Method.PUT, url, jo,
                    netHelper.authorizationToken(token),
                    { response ->
                        log.debug("DWAPAY", "on response sendCardResponseString")
                        try {
                            //creditcardid
                            val creditcardid = response.getString("creditcardid")
                            val numberalias = response.getString("numberalias")
                            val expirationdate = response.getString("expirationdate")
                            val activestate = response.getString("activestate")

                            val createOpt: String? = response.optString("create")
                            val createDate = createOpt?.let { create ->
                                DateTimeConversion.convertFromRFC3339(create)
                            }
                            val c = PaymentCardItem(creditcardid, numberalias,
                                    expirationdate, "EUR", null, activestate, null, createDate)
                            completion(c, null)
                        } catch (e: JSONException) {
                            completion(null, netHelper.ReplyParamsUnexpected(e))
                        }
                    }) { error -> completion(null, netHelper.GenericCommunicationError(error)) }

            request!!.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "transactions error", e)
            request = null
        }

        return request

    }


    fun getPaymentCards(token:String,
                       userid: String,
                       completion: (List<PaymentCardItem>?, Exception?) -> Unit): IRequest<*>? {

        val baseurl = netHelper.getURL("/rest/1.0/fin/creditcard/list")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", userid)
            val url = netHelper.getUrlDataString(baseurl, params)


            // Request a string response from the provided URL.
            val r = requestProvider.jsonArrayRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token),
                    { response: JSONArray ->

                        val creditcards = IntArray(response.length()) {i -> i}.map { i ->
                            val reply = response.getJSONObject(i)

                            PaymentCardItem(
                                    reply.optString("creditcardid"),
                                    reply.optString("numberalias"),
                                    reply.optString("expirationdate"),
                                    reply.optString("currency"),
                                    null,
                                    reply.optString("activestate"),
                                    null,
                                    null)
                        }

                        completion(creditcards, null)
                    })
            {error ->
                completion(null, error) }
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