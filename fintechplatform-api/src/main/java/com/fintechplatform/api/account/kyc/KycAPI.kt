package com.fintechplatform.api.account.kyc

import com.android.volley.Request
import com.fintechplatform.api.account.models.Account
import com.fintechplatform.api.account.models.KycRequested
import com.fintechplatform.api.account.models.KycStatus
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.*
import com.fintechplatform.api.user.models.DocType
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

open class KycAPI @Inject constructor(
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log,
        val netHelper: NetHelper) {

    private val TAG = "kycAPI"

    /**
     * Documents type required for the KYC process. Notice that the Nationality of User [userId] is mandatory.
     * Use [token] got from "Create User token" request.
     * [completion] callback contains the list of docType required.
     */
    open fun kycRequired(token: String,
                         account: Account,
                         completion: (List<DocType>?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/${account.tenantId}/${netHelper.getPathFromAccountType(account.accountType)}/${account.ownerId}/accounts/${account.accountId}/kycRequiredDocuments")
        var request: IRequest<*>?
        try {
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url, null,
                    netHelper.getHeaderBuilder().authorizationToken(token).getHeaderMap(), { response ->

                val jsonArray = response.getJSONArray("docType")

                val docTypes = (0 until jsonArray.length())
                        .map { jsonArray[it] as JSONArray }
                        .map { DocType.valueOf(it[0] as String) }
                completion(docTypes, null)
            }) { error ->
                completion(null, netHelper.createRequestError(error))
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "kycRequired", e)
            request = null
        }

        return request
    }

    /**
     * Needed to begin a KYC procedure.The validate [documentId] can take from 2h to 2 days to complete.
     */

    open fun kycProcedure(token: String,
                          account: Account,
                          documentId: UUID,
                          completion: (KycRequested?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/${account.tenantId}/${netHelper.getPathFromAccountType(account.accountType)}/${account.ownerId}/accounts/${account.accountId}/kycs")

        val jsonObject = JSONObject()
        jsonObject.put("documentId", documentId.toString())

        var request: IRequest<*>?
        try {
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.getHeaderBuilder().authorizationToken(token).getHeaderMap(), { response ->

                val error = response.optJSONObject("error")
                error?.let {
                    val rep =
                            try {
                                Error(ErrorCode.valueOf(error.getString("code")), error.getString("message"))
                            } catch (x: Exception) {
                                Error(ErrorCode.unknown_error, "[${error.getString("code")}] ${error.getString("message")}")
                            }

                    completion(null, NetHelper.APIResponseError(listOf(rep), null))
                    return@jsonObjectRequest
                }

                val kyc = KycRequested(
                        UUID.fromString(response.getString("kycId")),
                        UUID.fromString(response.getString("documentId")),
                        KycStatus.valueOf(response.getString("status")))
                completion(kyc, null)
            }) { error ->
                completion(null, netHelper.createRequestError(error))
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "kycRequired", e)
            request = null
        }
        return request
    }


}