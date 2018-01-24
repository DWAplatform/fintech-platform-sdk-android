package com.dwaplatform.android.enterprise.api

import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.enterprise.models.*
import com.dwaplatform.android.log.Log
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject

class EnterpriseAPI @Inject constructor(internal val hostName: String,
                                        internal val queue: IRequestQueue,
                                        internal val requestProvider: IRequestProvider,
                                        internal val log: Log,
                                        val netHelper: NetHelper) {
    private val TAG = "EnterpriseAPI"

    fun getEnterprise(token: String, userid: String, completion: (EnterpriseProfile?, Exception?) -> Unit): IRequest<*>? {
        val encodedUserId = URLEncoder.encode(userid, "UTF-8")
        val url = netHelper.getURL("/rest/v1/users/$encodedUserId/profile")

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
                   userid: String? = null,
                   name: String? = null,
                   telephone: String? = null,
                   email: String? = null,
                   enterpriseType: String? = null,
                   countryHeadquarters: String? = null,
                   city: String? = null,
                   address: String? = null,
                   zipcode: String? = null,
                   completion: (EnterpriseProfile?, Exception?) -> Unit): IRequest<*>? {



        val url = netHelper.getURL("/rest/1.0/user/profile")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()

            userid?.let { jsonObject.put("userid", userid) }
            name?.let { jsonObject.put("name", name) }
            telephone?.let { jsonObject.put("telephone", telephone) }
            enterpriseType?.let { jsonObject.put("enterpriseType", enterpriseType) }
            countryHeadquarters?.let { jsonObject.put("countryHeadquarters", countryHeadquarters) }
            address?.let { jsonObject.put("addressOfHeadquarters", address) }
            zipcode?.let { jsonObject.put("postalCodeHeadquarters", zipcode) }
            city?.let { jsonObject.put("cityOfHeadquarters", city) }
            email?.let { jsonObject.put("email", email) }

            val hparams: Map<String, String>
            if (userid != null) {
                hparams = netHelper.authorizationToken(token)
            } else {
                val h = HashMap<String, String>()
                h.put("Authorization", "Bearer " + token)
                hparams = h
            }

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    hparams,
                    { response ->

                        val userprofile = EnterpriseProfile( accountId =
                                response.getString("userid"))

                        completion(userprofile, null)
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
            log.error(TAG, "enterprise", e)
            request = null
        }

        return request
    }

    fun info(token: String,
             info: EnterpriseInfo,
             completion: (EnterpriseProfile?, Exception?) -> Unit) {

        enterprise(token = token,
                userid = info.accountId,
                name = info.name,
                enterpriseType = info.enterpriseType,
                completion = completion)

    }

    fun contacts(token: String,
                 contacts: EnterpriseContacts,
                 completion: (EnterpriseProfile?, Exception?) -> Unit) {

        enterprise(token = token,
                userid = contacts.accountId,
                email = contacts.email,
                telephone = contacts.telephone,
                completion = completion)
    }

    fun address(token: String,
                address: EnterpriseAddress,
                completion: (EnterpriseProfile?, Exception?) -> Unit) {

        enterprise(token = token,
                userid = address.accountId,
                address = address.address,
                zipcode = address.postalCode,
                city = address.city,
                countryHeadquarters = address.country,
                completion = completion)
    }

    fun getDocuments(token: String, userid: String, completion: (ArrayList<EnterpriseDocs?>?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/1.0/users/$userid/documents")

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

                            val enterpriseDocs = EnterpriseDocs(userid, jo.getString("doctype"), docpages)
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
                  accountId: String,
                  doctype: String,
                  documents: ArrayList<String?>,
                  idempotency: String,
                  completion: (Boolean, Exception?) -> Unit) : IRequest<*>? {
        val url = netHelper.getURL("/rest/1.0/users/$accountId/documents")
        var request : IRequest<*>?
        try {
            val ja = JSONArray()
            for(i in 0 until documents.size){
                ja.put(documents[i])
            }

            val jsonObject = JSONObject()
            jsonObject.put("doctype", doctype)
            jsonObject.put("pages", ja)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject, netHelper.authorizationToken(token), { response ->
                completion(true, null)
            }) { error ->
                val status = if (error.networkResponse != null)
                    error.networkResponse.statusCode else -1
                when (status) {
                    409 -> {
                        completion(false, netHelper.IdempotencyError(error))
                    }

                    401 -> {
                        completion(false, netHelper.TokenError(error))
                    }
                    else -> completion(false, netHelper.GenericCommunicationError(error))
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