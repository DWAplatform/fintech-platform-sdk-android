package com.fintechplatform.api.account.kyc

import com.android.volley.Request
import com.fintechplatform.api.account.models.Account
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.user.models.DocType
import org.json.JSONArray
import javax.inject.Inject

open class KycAPI @Inject constructor(
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log,
        val netHelper: NetHelper) {

    private val TAG = "kycAPI"

    open fun kycRequired(token: String,
                    account: Account,
                    completion: (List<DocType>?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/${account.tenantId}/${netHelper.getPathFromAccountType(account.accountType)}/${account.ownerId}/accounts/${account.accountId}/kycRequiredDocuments")
        var request: IRequest<*>?
        try {
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url, null,
                    netHelper.getHeaderBuilder().authorizationToken(token).getHeaderMap(), { response ->

                val jsonArray = response.getJSONArray("docType")

                var docTypes = (0 until jsonArray.length())
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
}