package com.fintechplatform.api.enterprise.api

import com.android.volley.Request
import com.fintechplatform.api.enterprise.models.*
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.profile.api.IdsDocumentsAPI
import org.json.JSONObject
import javax.inject.Inject

class EnterpriseAPI @Inject constructor(val restAPI: IdsDocumentsAPI,
                                        internal val hostName: String,
                                        internal val queue: IRequestQueue,
                                        internal val requestProvider: IRequestProvider,
                                        internal val log: Log,
                                        val netHelper: NetHelper) {
    private val TAG = "EnterpriseAPI"

    fun getEnterprise(token: String, enterpriseId: String, tenantId: String, completion: (EnterpriseProfile?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/enterprises/$enterpriseId")

        var request: IRequest<*>?
        try {
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token),
                    { response ->
                        val enterprise = searchEnterpriseReplyParser(response)
                        completion(enterprise, null)
                    })
            { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    403 ->
                        completion(null, netHelper.TokenError(error))
                    404 ->
                            completion(null, netHelper.EnterpriseNotFound(error))
                    else ->
                        completion(null, netHelper.createRequestError(error))
                }
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "getEnterprise", e)
            request = null
        }

        return request
    }

    fun enterprise(token: String,
                   enterpriseId: String,
                   tenantId: String,
                   name: String? = null,
                   telephone: String? = null,
                   email: String? = null,
                   enterpriseType: String? = null,
                   countryHeadquarters: String? = null,
                   city: String? = null,
                   address: String? = null,
                   postalCode: String? = null,
                   legalRepresentativeId: String?=null,
                   completion: (EnterpriseProfile?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/enterprises/$enterpriseId")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()

            name?.let { jsonObject.put("name", name) }
            telephone?.let { jsonObject.put("telephone", telephone) }
            enterpriseType?.let { jsonObject.put("enterpriseType", enterpriseType) }
            countryHeadquarters?.let { jsonObject.put("countryHeadquarters", countryHeadquarters) }
            address?.let { jsonObject.put("addressOfHeadquarters", address) }
            postalCode?.let { jsonObject.put("postalCodeHeadquarters", postalCode) }
            city?.let { jsonObject.put("cityOfHeadquarters", city) }
            email?.let { jsonObject.put("email", email) }
            legalRepresentativeId?.let { jsonObject.put("legalRepresentativeId", legalRepresentativeId) }

            val r = requestProvider.jsonObjectRequest(Request.Method.PUT, url, jsonObject,
                    netHelper.authorizationToken(token),
                    { response ->

                        val userprofile = EnterpriseProfile( enterpriseId =
                                response.getString("enterpriseId"))

                        completion(userprofile, null)
                    })
            { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    403 ->
                        completion(null, netHelper.TokenError(error))
                    404 ->
                        completion(null, netHelper.EnterpriseNotFound(error))
                    else ->
                        completion(null, netHelper.GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "enterprise", e)
            request = null
        }

        return request
    }

    fun info(token: String,
             info: EnterpriseInfo,
             completion: (EnterpriseProfile?, Exception?) -> Unit) {

        enterprise(token = token,
                tenantId = info.tenantId,
                enterpriseId = info.ownerId,
                name = info.name,
                enterpriseType = info.enterpriseType,
                completion = completion)

    }

    fun contacts(token: String,
                 contacts: EnterpriseContacts,
                 completion: (EnterpriseProfile?, Exception?) -> Unit) {

        enterprise(token = token,
                tenantId = contacts.tenantId,
                enterpriseId = contacts.ownerId,
                email = contacts.email,
                telephone = contacts.telephone,
                completion = completion)
    }

    fun address(token: String,
                address: EnterpriseAddress,
                completion: (EnterpriseProfile?, Exception?) -> Unit) {

        enterprise(token = token,
                tenantId = address.tenantId,
                enterpriseId = address.ownerId,
                address = address.address,
                postalCode = address.postalCode,
                city = address.city,
                countryHeadquarters = address.country,
                completion = completion)
    }
/*
    fun getDocuments(token: String, enterpriseId: String, tenantId: String, completion: (ArrayList<EnterpriseDocs?>?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/enterprises/$enterpriseId/documents/")

        var request: IRequest<*>?
        try {

            val r = requestProvider.jsonArrayRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token),
                    { response: JSONArray ->
                        val documents = ArrayList<EnterpriseDocs?>()

                        for(i in 0 until response.length()){

                            val jo = response.getJSONObject(i)
                            val pages = jo.getJSONArray("pages")
                            val docpages = arrayListOf<String?>()

                            for (j in 0 until pages.length()) {
                                docpages.add(pages.getString(j))
                            }

                            val enterpriseDocs = EnterpriseDocs(jo.getString("documentId"), jo.getString("docType"), docpages)
                            documents.add(enterpriseDocs)
                        }

                        completion(documents, null)
                    })
            { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    403 ->
                        completion(null, netHelper.TokenError(error))
                    else ->
                        completion(null, netHelper.GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "getDocuments", e)
            request = null
        }

        return request
    }
*/

    fun documents(token: String,
                  enterpriseId: String,
                  tenantId: String,
                  fileName: String?=null,
                  doctype: EnterpriseDocType,
                  documents: List<ByteArray>,
                  idempotency: String,
                  completion: (String?, Exception?) -> Unit) {

        val objectIds = mutableListOf<String>()

        documents.forEach { pageImage ->
            restAPI.addBucketForCompanyDocuments(token, tenantId, enterpriseId, fileName) { optBucketObject, optError ->
                log.debug("add Bucket", "1 " + pageImage.size)
                optError?.let{ error -> completion(null, error); return@addBucketForCompanyDocuments}
                optBucketObject?.let { bucketObject ->
                    bucketObject.uploadPath?.let { path ->
                        restAPI.uploadBucketObjectFile(token, path, pageImage) { optString, optError ->
                            log.debug("Upload Bucket", "2 " + pageImage.size)
                            optError?.let{ error -> completion(null, error); return@uploadBucketObjectFile}
                            optString?.let {
                                objectIds.add(bucketObject.objectId)
                                if(objectIds.size == documents.size) {
                                    log.debug("Create Documents", "3")
                                    restAPI.createEnterpriseDoc(token, enterpriseId, tenantId, doctype, objectIds, idempotency) { optUserDocuments, optError ->
                                        optError?.let{ error -> completion(null, error); return@createEnterpriseDoc}
                                        optUserDocuments?.let{
                                            completion(it, null)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun searchEnterpriseReplyParser(response: JSONObject) : EnterpriseProfile {
        return EnterpriseProfile(
                response.getString("enterpriseId"),
                response.optString("legalRepresentativeId"),
                response.optString("name"),
                response.optString("telephone"),
                response.optString("email"),
                response.optString("enterpriseType"),
                response.optString("countryHeadquarters"),
                response.optString("cityOfHeadquarters"),
                response.optString("addressOfHeadquarters"),
                response.optString("postalCodeHeadquarters")
        )
    }
}