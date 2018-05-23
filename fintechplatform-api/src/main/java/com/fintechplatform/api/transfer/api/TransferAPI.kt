package com.fintechplatform.api.transfer.api

import com.android.volley.Request
import com.fintechplatform.api.account.models.AccountType
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.transfer.models.TransferAccountModel
import org.json.JSONObject


class TransferAPI constructor(internal val hostName: String,
                              internal val queue: IRequestQueue,
                              internal val requestProvider: IRequestProvider,
                              internal val log: Log,
                              val netHelper: NetHelper) {

    val TAG = "TransferAPI"

    fun p2p(token: String,
            fromuserId: String,
            fromAccountId: String,
            fromAccountType: String,
            fromTenantId: String,
            touserid: String,
            toAccountId: String,
            toAccountType: String,
            toTenantId: String,
            message: String?,
            amount: Long,
            idempotency: String,
            completion: (Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$fromTenantId/${netHelper.getPathFromAccountType(AccountType.valueOf(fromAccountType))}/$fromuserId/accounts/$fromAccountId/transfers")

        var request: IRequest<*>?
        try {
            val joCredited = JSONObject()

            joCredited.put("ownerId", touserid)
            joCredited.put("accountId", toAccountId)
            joCredited.put("tenantId", toTenantId)
            joCredited.put("accountType", toAccountType)

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
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), {

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

    fun qrCredit(token: String,
                 tenantId: String,
                 ownerId: String,
                 accountId: String,
                 accountType: String,
                 message: String?,
                 amount: Long,
                 idempotency: String,
                 completion: (String?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/qrCreditTransfers")

        var request: IRequest<*>?
        try {
            val joCredited = JSONObject()

            joCredited.put("ownerId", ownerId)
            joCredited.put("accountId", accountId)
            joCredited.put("tenantId", tenantId)
            joCredited.put("accountType", accountType)

            val joAmount = JSONObject()
            joAmount.put("amount", amount)
            joAmount.put("currency", "EUR")

            // FIXME Fee JsonObject
            val joFee = JSONObject()
            joFee.put("amount", 0L)
            joFee.put("currency", "EUR")

            val jo = JSONObject()
            jo.put("creditedAccount", joCredited)
            jo.put("amount", joAmount)
            jo.put("fee", joFee)
            jo.putOpt("message", message)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jo,
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), {

                val error = it.optJSONObject("error")
                error?.let {
                    completion(null, netHelper.GenericCommunicationError(Exception(it.getString("message"))))
                    log.debug(TAG, it.getString("message"))
                    return@jsonObjectRequest
                }
                completion(it.getString("qrCreditTransferId"), null)
            }) { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    409 -> completion(null, netHelper.GenericCommunicationError(error))
                    401 -> completion(null, netHelper.TokenError(error))
                    else -> completion(null, netHelper.GenericCommunicationError(error))
                }
            }

            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "qrCredited", e)
            request = null
        }

        return request
    }

    fun getQr(token: String,
                  qrCreditTransferId: String,
                  tenantId: String,
                  completion: (TransferAccountModel?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/qrCreditTransfers/$qrCreditTransferId/details")

        var request: IRequest<*>?
        try {
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url, null,
                    netHelper.authorizationToken(token), {

                val error = it.optJSONObject("error")
                error?.let {
                    completion(null, netHelper.GenericCommunicationError(Exception(it.getString("message"))))
                    log.debug(TAG, it.getString("message"))
                    return@jsonObjectRequest
                }

                val joAccountRefDetail = it.getJSONObject("creditedAccount")
                val fullName = joAccountRefDetail.optString("name")?.run {
                    this
                }.plus(joAccountRefDetail.optString("surname")?.run{
                            " " + this
                        })
                val transferAcc = TransferAccountModel(
                        joAccountRefDetail.getString("ownerId"),
                        joAccountRefDetail.getString("accountId"),
                        joAccountRefDetail.getString("tenantId"),
                        joAccountRefDetail.getString("accountType"),
                        fullName,
                        joAccountRefDetail.optString("picture"),
                        joAccountRefDetail.getString("accountType"),
                        it.getString("created"),
                        it.getString("qrCreditTransferId"),
                        Money(it.getJSONObject("amount").getLong("amount")),
                        Money(it.getJSONObject("amount").getLong("amount")),
                        it.optString("message")
                )
                completion(transferAcc, null)
            }) { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    409 -> completion(null, netHelper.GenericCommunicationError(error))
                    401 -> completion(null, netHelper.TokenError(error))
                    else -> completion(null, netHelper.GenericCommunicationError(error))
                }
            }

            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "getQr", e)
            request = null
        }

        return request
    }
}