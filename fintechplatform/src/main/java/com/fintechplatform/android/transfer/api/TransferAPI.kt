package com.fintechplatform.android.transfer.api

import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.log.Log
import org.json.JSONObject


class TransferAPI constructor(internal val hostName: String,
                              internal val queue: IRequestQueue,
                              internal val requestProvider: IRequestProvider,
                              internal val log: Log,
                              val netHelper: NetHelper) {

    val TAG = "TransferAPI"

    fun p2p(token: String,
            fromuser: String,
            fromAccountId: String,
            fromTenantId: String,
            touserid: String,
            toAccountId: String,
            toTenantId: String,
            message: String,
            amount: Long,
            completion: (Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$fromTenantId/personal/$fromuser/accounts/$fromAccountId/transfers")

        var request: IRequest<*>?
        try {
            val joCredited = JSONObject()

            joCredited.put("ownerId", touserid)
            joCredited.put("accountId", toAccountId)
            joCredited.put("tenantId", toTenantId)

            val joAmount = JSONObject()
            joAmount.put("amount", amount)
            joAmount.put("currency", "EUR")

            // FIXME Currency on Money object and Fee JsonObject
            val joFee = JSONObject()
            joFee.put("amount", 0L)
            joFee.put("currency", "EUR")

            val jo = JSONObject()
            jo.put("creditedAccount", joCredited)
            jo.put("amount", joAmount)
            jo.put("fee", joFee)
            jo.putOpt("message", message)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jo,
                    netHelper.authorizationToken(token), {

                val error = it.optJSONObject("error")
                error?.let {
                    completion(netHelper.GenericCommunicationError(Exception(it.getString("message"))))
                    log.debug(TAG, it.getString("message"))
                    return@jsonObjectRequest
                }
                completion(null)
            }) { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    409 -> completion(netHelper.GenericCommunicationError(error))
                    401 -> completion(netHelper.TokenError(error))
                    else -> completion(netHelper.GenericCommunicationError(error))
                }
            }

            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "p2p", e)
            request = null
        }

        return request

    }
}