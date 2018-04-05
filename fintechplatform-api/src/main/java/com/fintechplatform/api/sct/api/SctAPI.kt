package com.fintechplatform.api.sct.api

import com.android.volley.Request
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import org.json.JSONObject


class SctAPI constructor(private val queue: IRequestQueue,
                         private val requestProvider: IRequestProvider,
                         private val log: Log,
                         val netHelper: NetHelper) {
    val TAG = "SctAPI"

    /*
        // SCT
    restCall(Method.GET, "/rest/v1/fintech/tenants/:tenantId/:accountType/:ownerId/accounts/:accountId/sctPayments?offset&limit", findAllSCTPaymentFromAccountId _),
    restCall(Method.GET, "/rest/v1/fintech/tenants/:tenantId/:accountType/:ownerId/accounts/:accountId/sctPayments/:sctPaymentId", findSCTPaymentFromTransactionId _),
     */
    fun sctPayment(token: String,
                   tenantId: String,
                   ownerId: String,
                   accountId: String,
                   accountType: String,
                   reciverIBAN: String,
                   reeciverName: String,
                   amount: Long,
                   message: String?,
                   executionDate: String?,
                   urgent: Boolean,
                   instant: Boolean,
                   idempotency: String,
                   completion: (Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/${netHelper.getPathFromAccountType(accountType)}/$ownerId/accounts/$accountId/sctPayments")

        var request: IRequest<*>?
        try {
            val joAmount = JSONObject()
            joAmount.put("amount", amount)
            joAmount.put("currency", "EUR")

            // FIXME Currency on Money object and Fee JsonObject
            val joFee = JSONObject()
            joFee.put("amount", 0L)
            joFee.put("currency", "EUR")

            val jo = JSONObject()
            jo.put("receiverIBAN", reciverIBAN)
            jo.put("reeciverName", reeciverName)
            jo.put("instant", instant)
            jo.put("urgent", urgent)
            jo.put("executionDate", executionDate)
            jo.put("amount", joAmount)
            jo.put("fee", joFee)
            jo.putOpt("message", message)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jo,
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), {

                val error = it.optJSONObject("error")
                error?.let {
                    completion(netHelper.GenericCommunicationError(Exception(it.getString("message"))))
                    log.debug(TAG, it.getString("message"))
                    return@jsonObjectRequest
                }

                /*
                case class SCTPayment(tenantId: UUID,
                      ownerId: UUID,
                      accountId: UUID,
                      transactionId: UUID,
                      receiverIBAN: String,
                      receiverName: String,
                      amount: Money,
                      fee: Money,
                      message: Option[String],
                      executionDate: String,
                      urgent: Boolean,
                      instant: Boolean,
                      status: String,
                      error: Option[Error],
                      created: String,
                      updated: String)
                 */
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