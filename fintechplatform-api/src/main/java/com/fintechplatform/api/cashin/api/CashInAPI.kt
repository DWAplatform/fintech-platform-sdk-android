package com.fintechplatform.api.cashin.api

import com.android.volley.Request
import com.fintechplatform.api.cashin.models.CashInReply
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import org.json.JSONObject
import javax.inject.Inject

/**
 * CashIn API class performs a cashIn request to Fintech Platform.
 */
open class CashInAPI @Inject constructor(
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log,
        val netHelper: NetHelper) {

    private val TAG = "CashInAPI"

    /**
     * cashIn transfers money from Linked Card [cardId] to the Fintech Account, identified from [tenantId] [ownerId] [accountType] and [accountId] params.
     * You have to set the amount and currency to transfer, both to set into [amount] param.
     * Use [token] got from "Create User token" request.
     * Use [idempotency] parameter to avoid multiple inserts.
     * [completion] callback contains transactionId. Check if the Card issuer requires to perform a Secure3D procedure.
     * Whether secure3D is required, you will find the specific redirect URL.
     */
    open fun cashIn(token: String,
                    ownerId: String,
                    accountId: String,
                    accountType: String,
                    tenantId: String,
                    cardId: String,
                    amount: Money,
                    idempotency: String,
                    completion: (CashInReply?, Exception?) -> Unit): IRequest<*>? {
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

                completion(CashInReply(transactionid, securecodeneeded, redirecturl), null)
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
            log.error(TAG, "cashIn", e)
            request = null
        }

        return request
    }

}