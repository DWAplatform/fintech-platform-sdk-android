package com.fintechplatform.android.card.api

import com.fintechplatform.android.card.helpers.PaymentCardHelper
import com.fintechplatform.android.card.models.PaymentCardItem
import com.fintechplatform.android.log.Log
import org.json.JSONArray
import java.lang.Exception
import javax.inject.Inject

/**
 * Main class for API communication with FintechPlatform to handle Cards.
 * Please use FintechPlatform to get PaymentCard API instance; do not create directly
 *
 */
open class PaymentCardAPI @Inject constructor(
        internal val restAPI: PaymentCardRestAPI,
        internal val log: Log,
        internal val paymentCardHelper: PaymentCardHelper) {

    /**
     * Represent the error send as reply from FintechPlatform API.
     *
     * @property json error as json array, can be null in case of json parsing error
     * @property throwable error returned from the underlying HTTP library
     */
    data class APIReplyError(val json: JSONArray?, val throwable: Throwable) : Exception(throwable)

    /**
     * Represent the error during FintechPlatform API response parse.
     *
     * @property throwable error returned from parser
     */
    data class ParseReplyParamsException(val throwable: Throwable) : Exception(throwable)

    private val TAG = "ClientCardAPI"

    /**
     *  Register a PaymentCard. Use this method to register a user card and PLEASE DO NOT save card information on your own client or server side.
     *
     *  @property token token returned from FintechPlatform to the create card request.
     *  @property cardNumber 16 digits user card number, without spaces or dashes
     *  @property expiration card expiration date in MMYY format
     *  @property cxv  3 digit cxv card number
     *  @property completionHandler callback called after the server communication is done and containing a PaymentCard object or an Exception in case of error.
     */
    open fun registerCard(token: String,
                          userId: String,
                          accountId: String,
                          tenantId: String,
                          cardNumber: String,
                          expiration: String,
                          cvxValue: String,
                          currency: String,
                          completionHandler: (PaymentCardItem?, Exception?) -> Unit) {

        log.debug(TAG, "fun registerCard called")


        paymentCardHelper.checkCardFormat(cardNumber, expiration, cvxValue)

        restAPI.createCreditCardRegistration(userId, accountId, tenantId, token, paymentCardHelper.generateAlias(cardNumber), expiration, currency){ optCardReg, optError ->
            optError?.let { error -> completionHandler(null, error); return@createCreditCardRegistration }
            optCardReg?.let { cr->
                restAPI.getCardSafe(PaymentCardRestAPI.CardToRegister(cardNumber, expiration, cvxValue), userId, accountId, tenantId, token) { optCardSafe, optErrorCS ->
                    optErrorCS?.let { completionHandler(null, it); return@getCardSafe}
                    optCardSafe?.let { cardToRegister ->
                        restAPI.postCardRegistrationData(userId, accountId, tenantId, token, cardNumber, expiration, cvxValue, cr) { paymentCardItem, exception ->
                            exception?.let { completionHandler(null, it); return@postCardRegistrationData }
                            paymentCardItem?.let { completionHandler(it, null) }
                        }
                    }
                }
            }
        }
    }
}