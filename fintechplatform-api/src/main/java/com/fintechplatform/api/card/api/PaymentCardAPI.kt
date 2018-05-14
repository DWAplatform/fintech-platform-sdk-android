package com.fintechplatform.api.card.api

import com.fintechplatform.api.account.models.Account
import com.fintechplatform.api.card.helpers.PaymentCardHelper
import com.fintechplatform.api.card.models.PaymentCard
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.money.Currency
import com.fintechplatform.api.net.IRequest
import java.lang.Exception
import java.util.*
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
     * Represent the error during FintechPlatformAPI API response parse.
     *
     * [throwable] error returned from parser
     */
    data class ParseReplyParamsException(val throwable: Throwable) : Exception(throwable)

    private val TAG = "ClientCardAPI"

    /*
    registerPaymentCard(token: String, account: Account, alias, expiration, currency, idempotencyKey: String?, completionHandler: (PaymentCard?, Exception?))
     */
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
                          account: Account,
                          cardNumber: String,
                          expiration: String,
                          cvxValue: String,
                          currency: Currency,
                          idempotencyKey: String? = null,
                          completionHandler: (PaymentCard?, Exception?) -> Unit) {

        log.debug(TAG, "fun registerCard called")


        paymentCardHelper.checkCardFormat(cardNumber, expiration, cvxValue)

        restAPI.createCreditCardRegistration(account.ownerId.toString(),
                account.accountId.toString(),
                account.accountType,
                account.tenantId.toString(),
                token,
                paymentCardHelper.generateAlias(cardNumber),
                expiration,
                currency){ optCardReg, optError ->

            optError?.let { error -> completionHandler(null, error); return@createCreditCardRegistration }
            optCardReg?.let { crReply->
                restAPI.getCardSafe(PaymentCardRestAPI.CardToRegister(cardNumber, expiration, cvxValue),
                        account.ownerId.toString(),
                        account.accountId.toString(),
                        account.accountType,
                        account.tenantId.toString(),
                        token) { optCardSafe, optErrorCS ->
                    optErrorCS?.let { completionHandler(null, it); return@getCardSafe}
                    optCardSafe?.let { cardToRegister ->
                        restAPI.postCardRegistrationData(account.ownerId.toString(),
                                account.accountId.toString(),
                                account.accountType,
                                account.tenantId.toString(),
                                token,
                                cardToRegister.cardNumber,
                                cardToRegister.expiration,
                                cardToRegister.cvx,
                                crReply) { paymentCardItem, exception ->
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
    open fun getPaymentCards(token:String,
                        account: Account,
                        completion: (List<PaymentCard>?, Exception?) -> Unit): IRequest<*>? {

        return restAPI.getPaymentCards(token, account.ownerId.toString(), account.accountId.toString(), account.accountType, account.tenantId.toString(), completion)
    }

    /**
     * Delete specific Payment Cards [cardId] linked to Fintech Platform Account.
     * Fitench Platform Account is identified from [tenantId] [ownerId] [accountType] and [accountId] params.
     *  [token] Fintech Platform API token got from "Create User token" request.
     *  [completion] callback returns the list of cards or an Exception if occurent some errors
     */
    open fun deletePaymentCard(token:String,
                               account: Account,
                               cardId: String,
                               completion: (Exception?) -> Unit): IRequest<*>? {
        return restAPI.deletePaymentCard(token, account.ownerId.toString(), account.accountId.toString(), account.accountType, account.tenantId.toString(), cardId, completion)
    }

    open fun setDefaultPaymentCard(token:String,
                                   account: Account,
                                   cardId: String,
                                   completion: (PaymentCard?, Exception?) -> Unit): IRequest<*>? {
        return restAPI.setDefaultCard(token, account.ownerId.toString(), account.accountId.toString(), account.accountType, account.tenantId.toString(), cardId, completion)
    }
}