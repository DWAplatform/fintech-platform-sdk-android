package com.fintechplatform.api.cashout.api

import com.android.volley.Request
import com.fintechplatform.api.account.models.AccountType
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.money.Currency
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

/**
 * CashOut API class performs a cashOut request to Fintech Platform.
 */
class CashOutAPI @Inject constructor(internal val hostName: String,
                                     internal val queue: IRequestQueue,
                                     internal val requestProvider: IRequestProvider,
                                     internal val log: Log,
                                     val netHelper: NetHelper) {

    private val TAG = "CashOutAPI"

    /**
     * CashOut transfers money from Fintech Account, identified from [tenantId] [ownerId] [accountType] and [accountId] params, to linked bank account [linkedBankId]
     * You have to set the [amount] to transfer.
     * Use [token] got from "Create User token" request.
     * Use [idempotency] parameter to avoid multiple inserts.
     */
    fun cashOut(token: String,
                ownerId: String,
                accountId: String,
                accountType: String,
                tenantId: String,
                linkedBankId: String,
                amount: Long,
                idempotency: String,
                completion: (Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(AccountType.valueOf(accountType))}/$ownerId/accounts/$accountId/linkedBanks/$linkedBankId/cashOuts")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            val joAmount = JSONObject()
            joAmount.put("amount", amount)
            joAmount.put("currency", "EUR")
            jsonObject.put("amount", joAmount)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), { _ ->
                completion(null)
            }) { error ->


                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 -> {
                        completion(netHelper.TokenError(error))
                    }
                    409 -> {
                        completion(netHelper.IdempotencyError(error))
                    }
                    else -> completion(netHelper.GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "cashOut", e)
            request = null
        }

        return request
    }

    /**
     * Gets the Fee from Cash Out linked bank account [linkedBankId] to Fintech Account, identified from [tenantId] [ownerId] [accountType] and [accountId] params.
     * Set the [amount] in which [completion] fee will be estimate of.
     * Use [token] got from "Create User token" request.
     */
    fun cashOutFee(token: String,
                   tenantId: String,
                   accountId: String,
                   ownerId: String,
                   accountType: String,
                   linkedBankId: String,
                   amount: Money,
                   completion: (Money?, Exception?) -> Unit): IRequest<*>? {


        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(AccountType.valueOf(accountType))}/$ownerId/accounts/$accountId/linkedBanks/$linkedBankId/cashOutsFee")

        val params = HashMap<String, Any>()
        params["amount"] = amount.value.toString()
        params["currency"] = amount.currency
        val rurl = netHelper.getUrlDataString(url, params)

        var request: IRequest<*>?
        try {
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, rurl, null,
                    netHelper.getHeaderBuilder().authorizationToken(token).getHeaderMap(), { response ->

                val amountResp = response.getLong("amount")
                val currency = response.optString("currency")

                completion(Money(amountResp, Currency.valueOf(currency)), null)
            }) { error ->

                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 -> {
                        completion(null, netHelper.TokenError(error))
                    }

                    409 -> {
                        completion(null, netHelper.IdempotencyError(error))
                    }
                    else -> completion(null, netHelper.GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "cashOut", e)
            request = null
        }

        return request
    }
}