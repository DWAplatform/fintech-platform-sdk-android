package com.fintechplatform.api.profile.api

import com.android.volley.Request
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.profile.models.DocType
import com.fintechplatform.api.profile.models.UserDocuments
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


class IdsDocumentsAPI @Inject constructor(internal val hostName: String,
                                          internal val queue: IRequestQueue,
                                          internal val requestProvider: IRequestProvider,
                                          internal val log: Log,
                                          val netHelper: NetHelper) {
    private val TAG = "IdsDocumentsAPI"

    internal data class BucketObject(val bucketName: String,
                                     val objectId: String,
                                     val status: String?,
                                     val filename: String?,
                                     val uploadPath: String?)

    internal fun addBucketForUserDocuments(token: String,
                                           tenantId: String,
                                           userId: String,
                                           filename: String?=null,
                                           callback: (BucketObject?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/users/$userId/bucketObjects")

        var request: IRequest<*>?
        try {
            val jo = JSONObject()
            jo.put("fileName", filename)

            request = requestProvider.jsonObjectRequest(Request.Method.POST, url, jo,
                    netHelper.authorizationToken(token),
                    { response ->
                        log.debug(TAG, "on response addBucketForImage")
                        try {
                            val bucketName = response.getString("bucketName")
                            val objectId = response.getString("objectId")
                            val status = response.optString("status")
                            val uploadPath = response.optString("uploadPath")

                            val bucket = BucketObject(bucketName, objectId, status, filename, uploadPath)

                            callback(bucket, null)
                        } catch (e: JSONException) {
                            callback(null, netHelper.ReplyParamsUnexpected(e))
                            log.error(TAG, "addBucketForUserDocuments error", e)
                        }
                    }) { error ->
                callback(null, netHelper.createRequestError(error))
            }

            request.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "createCreditCardRegistration error", e)
            request = null
        }

        return request
    }

    internal fun uploadBucketObjectFile(token: String, uploadPath: String, payload: ByteArray, callback: (String?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL(uploadPath)
        var request: IRequest<*>?
        try {

            request = requestProvider.byteArrayRequest(Request.Method.POST, url, payload,
                    netHelper.authorizationToken(token),
                    { response ->
                        log.debug(TAG, "on response uploadBucketObjectFile")
                        try {
                            callback(response, null)
                        } catch (e: JSONException) {
                            callback(null, netHelper.ReplyParamsUnexpected(e))
                            log.error(TAG, "uploadBucketObjectFile error", e)
                        }
                    }) { error ->
                callback(null, netHelper.createRequestError(error))
            }

            request.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(request)

        } catch (e: Exception) {
            log.error(TAG, "createCreditCardRegistration error", e)
            request = null
        }

        return request
    }
    
    internal fun createDocuments(token: String,
                                 userId: String,
                                 tenantId: String,
                                 doctype: DocType,
                                 objectIds: List<String?>,
                                 idempotency: String,
                                 completion: (UserDocuments?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/users/$userId/documents")

        var request : IRequest<*>?

        try {
            val ja = JSONArray()
            for(i in 0 until objectIds.size){
                ja.put(objectIds[i])
            }

            val jsonObject = JSONObject()
            jsonObject.put("docType", doctype.toString())
            jsonObject.put("bucketObjectIdPages", ja)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(),
                    { response ->
                        log.debug("UserDocument", response.toString())
                        val documentId = response.optString("documentId")
                        val docType = response.optString("docType")?.run { DocType.valueOf(this) }
                        val bucketObjectIdPages = response.optJSONArray("bucketObjectIdPages")?.run {
                            val bucketObjIds = mutableListOf<String?>()
                            for (j in 0 until length()) {
                                bucketObjIds.add(getString(j))
                                log.debug("bucket ObjectId", getString(j))
                            }
                            bucketObjIds.filterNotNull()
                        }
                        completion(UserDocuments(userId, documentId, docType, bucketObjectIdPages), null)
            }) { error ->
                val status = if (error.networkResponse != null)
                    error.networkResponse.statusCode else -1
                when (status) {
                    409 -> {
                        completion(null, netHelper.IdempotencyError(error))
                    }

                    401 -> {
                        completion(null, netHelper.TokenError(error))
                    }
                    else -> completion(null, netHelper.GenericCommunicationError(error))
                }
            }

            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception){
            log.error(TAG, "documents", e)
            request = null
        }

        return request
    }
}