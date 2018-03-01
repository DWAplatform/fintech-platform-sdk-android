package com.fintechplatform.android.enterprise.api

import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.enterprise.models.*
import com.fintechplatform.android.log.Log
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

class EnterpriseAPI @Inject constructor(internal val hostName: String,
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
                        val enterprise = netHelper.searchEnterpriseReplyParser(response)
                        completion(enterprise, null)
                    })
            { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 ->
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
                    401 ->
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

                            val enterpriseDocs = EnterpriseDocs(jo.getString("documentId"), jo.getString("doctype"), docpages)
                            documents.add(enterpriseDocs)
                        }

                        completion(documents, null)
                    })
            { error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 ->
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

    fun documents(token: String,
                  enterpriseId: String,
                  tenantId: String,
                  doctype: String,
                  documents: ArrayList<String?>,
                  idempotency: String,
                  completion: (String?, Exception?) -> Unit) : IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/enterprises/$enterpriseId/documents")
        var request : IRequest<*>?
        try {
            val ja = JSONArray()
            for(i in 0 until documents.size){
                ja.put(documents[i])
            }

            val jsonObject = JSONObject()
            jsonObject.put("doctype", doctype)
            jsonObject.put("pages", ja)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject, netHelper.getHeaderBuilder().authorizationToken(token).idempotency(idempotency).getHeaderMap(), { response ->
                completion(jsonObject.getString("documentId"), null)
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