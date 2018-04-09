package com.fintechplatform.api.card.api

import com.fintechplatform.api.card.helpers.PaymentCardHelper
import com.fintechplatform.api.card.models.PaymentCardItem
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequest
import org.json.JSONArray
import java.lang.Exception
import javax.inject.Inject

/**
 * Main class for API communication with FintechPlatformAPI to handle Cards.
 * Please use FintechPlatformAPI to get PaymentCard API instance; do not create directly
 *
 */
open class PaymentCardAPI @Inject constructor(
        internal val restAPI: PaymentCardRestAPI,
        internal val log: Log,
        internal val paymentCardHelper: PaymentCardHelper) {

    /**
     * Represent the error send as reply from Fintech Platform API.
     *
     * [json] error as json array, can be null in case of json parsing error
     * [throwable] error returned from the underlying HTTP library
     */
    data class APIReplyError(val json: JSONArray?, val throwable: Throwable) : Exception(throwable)

    /**
     * Represent the error during FintechPlatformAPI API response parse.
     *
     * [throwable] error returned from parser
     */
    data class ParseReplyParamsException(val throwable: Throwable) : Exception(throwable)

    private val TAG = "ClientCardAPI"

    /**
     *  Register a PaymentCard. Use this method to register a user card and PLEASE DO NOT save card information on your own client or server side.
     *
     *  This request also links given card to the Fintech Platform Account, identified from [tenantId] [ownerId] [accountType] and [accountId] params.
     *  [token] Fintech Platform API token got from "Create User token" request.
     *  [cardNumber] 16 digits user card number, without spaces or dashes
     *  [expiration] card expiration date in MMYY format
     *  [cvxValue] 3 digit cxv card number
     *  [currency] card default currency
     *  [completionHandler] callback called after the server communication is done and containing a PaymentCard item object or an Exception in case of error.
     */
    open fun registerCard(token: String,
                          ownerId: String,
                          accountId: String,
                          accountType: String,
                          tenantId: String,
                          cardNumber: String,
                          expiration: String,
                          cvxValue: String,
                          currency: String,
                          completionHandler: (PaymentCardItem?, Exception?) -> Unit) {

        log.debug(TAG, "fun registerCard called")


        paymentCardHelper.checkCardFormat(cardNumber, expiration, cvxValue)

        restAPI.createCreditCardRegistration(ownerId, accountId, accountType, tenantId, token, paymentCardHelper.generateAlias(cardNumber), expiration, currency){ optCardReg, optError ->
            optError?.let { error -> completionHandler(null, error); return@createCreditCardRegistration }
            optCardReg?.let { cr->
                restAPI.getCardSafe(PaymentCardRestAPI.CardToRegister(cardNumber, expiration, cvxValue), ownerId, accountId, accountType, tenantId, token) { optCardSafe, optErrorCS ->
                    optErrorCS?.let { completionHandler(null, it); return@getCardSafe}
                    optCardSafe?.let { cardToRegister ->
                        restAPI.postCardRegistrationData(ownerId, accountId, accountType, tenantId, token, cardToRegister.cardNumber, cardToRegister.expiration, cardToRegister.cvx, cr) { paymentCardItem, exception ->
                            exception?.let { completionHandler(null, it); return@postCardRegistrationData }
                            paymentCardItem?.let { completionHandler(it, null) }
                        }
                    }
                }
            }
        }
    }

    /**
     * Get Payment Cards linked to Fintech Platform Account, identified from [tenantId] [ownerId] [accountType] and [accountId] params.
     *  [token] Fintech Platform API token got from "Create User token" request.
     *  [completion] callback returns the list of cards or an Exception if occurent some errors
     */
    fun getPaymentCards(token:String,
                        ownerId: String,
                        accountId: String,
                        accountType: String,
                        tenantId: String,
                        completion: (List<PaymentCardItem>?, Exception?) -> Unit): IRequest<*>? {

        return restAPI.getPaymentCards(token, ownerId, accountId, accountType, tenantId, completion)
    }
}