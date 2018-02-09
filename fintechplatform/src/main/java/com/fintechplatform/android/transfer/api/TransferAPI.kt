package com.fintechplatform.android.transfer.api

import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.log.Log
import com.fintechplatform.android.transfer.contactslist.models.NetworkUserModel
import org.json.JSONObject


class TransferAPI constructor(internal val hostName: String,
                              internal val queue: IRequestQueue,
                              internal val requestProvider: IRequestProvider,
                              internal val log: Log,
                              val netHelper: NetHelper) {

    val TAG = "TransferAPI"

    fun p2p(token: String,
            fromuserid: String,
            touserid: String,
            message: String,
            amount: Long,
            idempotency: String?,
            notificationtype: String? = null,
            completion: (Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/1.0/fin/p2p")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            jsonObject.put("fromuserid", fromuserid)
            jsonObject.put("touserid", touserid)
            jsonObject.put("message", message)
            jsonObject.put("amount", amount)
            jsonObject.put("notificationtype", notificationtype)
            jsonObject.put("idempotency", idempotency)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.authorizationToken(token), {
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

    // TODO da spostare su sample
    fun p2pPeersFiltered(token: String,
                         userid: String,
                         telnumbers: List<String>,
                         completion: (List<NetworkUserModel>?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/1.0/fin/p2p/peersfiltered")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            jsonObject.put("userid", userid)
            jsonObject.put("telnumbers", telnumbers)

            val r = requestProvider.jsonObjectRequestArrayReply(Request.Method.POST, url, jsonObject,
                    netHelper.authorizationToken(token), { response ->

                val p2ps = mutableListOf<NetworkUserModel>()

                for (i in 0 until response.length()) {

                    val jo = response.getJSONObject(i)

                    val p2pu = NetworkUserModel(
                            jo.getString("userid"),
                            jo.getString("name"),
                            jo.getString("surname"),
                            jo.getString("telephone"),
                            if (jo.has("photo") && jo.getString("photo").isNotEmpty())
                                jo.getString("photo") else null,
                            //  FIXME NetworkUsers usertype bank or e-wallet
                            if (!jo.has("type") && jo.getString("type").isNotEmpty())
                                jo.getString("type") else null)

                    p2ps.add(p2pu)
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