package com.fintechplatform.api.pagopa.api

import com.android.volley.Request
import com.fintechplatform.api.account.models.AccountType
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import org.json.JSONObject
import javax.inject.Inject


class PagoPaAPI @Inject constructor(internal val hostName: String,
                                    internal val queue: IRequestQueue,
                                    internal val requestProvider: IRequestProvider,
                                    internal val log: Log,
                                    val netHelper: NetHelper) {

    private val TAG = "PagoPaAPI"

    fun pagPA(token: String,
              ownerId: String,
              accountId: String,
              accountType: String,
              tenantId: String,
              operatorId: String,
              documentId: String,
              executionDate: String,
              idempotency: String,
              completion: (Boolean?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(AccountType.valueOf(accountType))}/$ownerId/accounts/$accountId/pagoPA")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()

            jsonObject.put("operatorId", operatorId )
            jsonObject.put("documentId", documentId)
            jsonObject.put("executionDate", executionDate)


            // [{"code":"asp_not_implemented","message":"Function: [pagoPA#Personal] not implemented in Mangopay"}]
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), { _ ->

                completion(true, null)
            }) { error ->

                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 -> {
                        completion(false, netHelper.TokenError(error))
                    }

                    409 -> {
                        completion(false, netHelper.IdempotencyError(error))
                    }
                    else -> completion(false, netHelper.GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "PagoPaAPI", e)
            request = null
        }

        return request
    }
}
