package com.fintechplatform.api.payin.api

import com.android.volley.Request
import com.fintechplatform.api.card.helpers.DateTimeConversion
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.*
import com.fintechplatform.api.payin.models.PayInReply
import com.fintechplatform.api.payin.models.PayInStatus
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

/**
 * PayIn API class performs a payIn request to Fintech Platform.
 */
open class PayInAPI @Inject constructor(
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log,
        val netHelper: NetHelper) {

    private val TAG = "PayInAPI"

    /**
     * payIn transfers money from Linked Card [cardId] to the Fintech Account, identified from [tenantId] [ownerId] [accountType] and [accountId] params.
     * You have to set the amount and currency to transfer, both to set into [amount] param.
     * Use [token] got from "Create User token" request.
     * Use [idempotency] parameter to avoid multiple inserts.
     * [completion] callback contains transactionId. Check if the Card issuer requires to perform a Secure3D procedure.
     * Whether secure3D is required, you will find the specific redirect URL.
     */
    open fun payIn(token: String,
                    ownerId: String,
                    accountId: String,
                    accountType: String,
                    tenantId: String,
                    cardId: String,
                    amount: Money,
                    idempotency: String,
                    completion: (PayInReply?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$ownerId/accounts/$accountId/linkedCards/$cardId/cashIns")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()

            val joAmount = JSONObject()
            joAmount.put("amount", amount.value )
            joAmount.put("currency", amount.currency)

            jsonObject.put("amount", joAmount)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), { response ->

                val transactionid = response.getString("transactionId")
                val securecodeneeded = response.getBoolean("secure3D")
                val redirecturl = response.optString("redirectURL")
                val status = response.optString("status")
                if (PayInStatus.valueOf(status) == PayInStatus.FAILED) {
                    val error = response.getJSONObject("error")

                    val rep =
                    try {
                        Error(ErrorCode.valueOf(error.getString("code")), error.getString("message"))
                    } catch (x: Exception) {
                        Error(ErrorCode.unknown_error, "[${error.getString("code")}] ${error.getString("message")}")
                    }

                    completion(null, NetHelper.APIResponseError(listOf(rep), null))
                    return@jsonObjectRequest
                }
                val created = response.optString("created").let {
                    DateTimeConversion.convertFromRFC3339(it)
                }
                val updated = response.optString("updated").let {
                    DateTimeConversion.convertFromRFC3339(it)
                }

                completion(PayInReply(transactionid, securecodeneeded, redirecturl, PayInStatus.valueOf(status), created, updated), null)
            }) { error ->
                completion(null, netHelper.createRequestError(error))
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "payIn", e)
            request = null
        }

        return request
    }

    /**
     * Gets Fee from Cash in performed by linked card [cardId].
     * Card is linked to Fintech Account, identified from [tenantId] [ownerId] [accountType] and [accountId] params.
     * Set the [amount] in which [completion] fee will be estimate of.
     * Use [token] got from "Create User token" request.
     */
    fun payInFee(token: String,
                  tenantId: String,
                  accountId: String,
                  ownerId: String,
                  accountType: String,
                  cardId: String,
                  amount: Money,
                  completion: (Money?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$ownerId/accounts/$accountId/linkedCards/$cardId/cashInsFee")

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

                completion(Money(amountResp, currency), null)
            }) { error ->
                completion(null, netHelper.createRequestError(error))
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "payInFee", e)
            request = null
        }

        return request
    }

}