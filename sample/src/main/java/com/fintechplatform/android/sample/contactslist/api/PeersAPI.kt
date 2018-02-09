package com.fintechplatform.android.sample.contactslist.api

import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.log.Log
import com.fintechplatform.android.sample.contactslist.models.NetworkAccounts


class PeersAPI constructor(internal val queue: IRequestQueue,
                          internal val requestProvider: IRequestProvider,
                          internal val log: Log,
                          val netHelper: NetHelper) {

    val TAG = "PeersAPI"

    fun p2pPeersFiltered(token: String,
                         userId: String,
                         accountId: String,
                         tenantId: String,
                         completion: (List<NetworkAccounts>?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/mobile/tenants/$tenantId/users/$userId/accounts/$accountId/peersAccount")

        var request: IRequest<*>?
        try {
            val r = requestProvider.jsonObjectRequestArrayReply(Request.Method.GET, url, null,
                    netHelper.authorizationToken(token), { response ->

                val p2ps = mutableListOf<NetworkAccounts>()

                for (i in 0 until response.length()) {

                    val jo = response.getJSONObject(i)
                    val joUser = jo.getJSONObject("person")

                    val peerAccount = NetworkAccounts(
                            jo.getString("ownerId"),
                            jo.getString("accountId"),
                            jo.getString("tenantId"),
                            joUser.getString("name"), joUser.getString("surname"),
                            if (joUser.has("photo") && joUser.getString("photo").isNotEmpty())
                                jo.getString("photo") else null,
                            jo.getString("aspName"),
                            jo.getString("accountType"))

                    p2ps.add(peerAccount)
                }
                completion(p2ps, null)

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
            log.error(TAG, "p2pPeersFiltered", e)
            request = null
        }

        return request

    }
}