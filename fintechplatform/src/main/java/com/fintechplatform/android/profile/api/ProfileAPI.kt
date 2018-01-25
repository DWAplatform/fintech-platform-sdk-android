package com.fintechplatform.android.profile.api

import com.android.volley.Request
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestProvider
import com.fintechplatform.android.api.IRequestQueue
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.iban.models.UserResidential
import com.fintechplatform.android.log.Log
import com.fintechplatform.android.profile.models.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject


class ProfileAPI @Inject constructor(
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log,
        val netHelper: NetHelper) {

    private val TAG = "ProfileAPI"
    /*
    // pertinenza della registrazione utente
    fun searchUser(telephone: String,
                   phonetoken: String,
                   completion: (UserProfile?, Exception?) -> Unit): IRequest<*>? {
        val encodedTelephone = URLEncoder.encode(telephone, "UTF-8")
        val url = netHelper.getURL("/rest/v1/telephonenumbers/$encodedTelephone/user")

        var request: IRequest<*>?
        try {
            val hparams = HashMap<String, String>()
            hparams.put("Authorization", "Bearer " + phonetoken)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url, null, hparams,
                    { response ->
                        val (userprofile, error) = netHelper.searchUserReplyParser(response)
                        completion(userprofile, error)
                    })
            {error ->
                // user not found
                if (error.networkResponse.statusCode == 403) {
                    completion(null, null)
                    return@jsonObjectRequest
                }

                completion(null, error) }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "searchUser", e)
            request = null
        }

        return request
    }

    */

    fun searchUser(token: String, userid: String, completion: (UserProfile?, Exception?) -> Unit): IRequest<*>? {
        val encodedUserId = URLEncoder.encode(userid, "UTF-8")
        val url = netHelper.getURL("/rest/v1/users/$encodedUserId/profile")

        var request: IRequest<*>?
        try {
            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token),
                    { response ->
                        val (userprofile, error) = netHelper.searchUserReplyParser(response)
                        completion(userprofile, error)
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
            log.error(TAG, "searchUser", e)
            request = null
        }

        return request
    }


    private fun profile(token: String,
                 userid: String? = null,
                 name: String? = null,
                 surname: String? = null,
                 nationality: String? = null,
                 countryofresidence: String? = null,
                 birthday: String? = null,
                 address: String? = null,
                 zipcode: String? = null,
                 city: String? = null,
                 photo: String? = null,
                 telnum: String? = null,
                 email: String? = null,
                 phonetoken: String? = null,
                 jobinfo: String? = null,
                 income: String? = null,
                 completion: (UserProfileReply?, Exception?) -> Unit): IRequest<*>? {


        val url = netHelper.getURL("/rest/1.0/user/profile")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()

            userid?.let { jsonObject.put("userid", userid) }
            name?.let { jsonObject.put("name", name) }
            surname?.let { jsonObject.put("surname", surname) }
            nationality?.let { jsonObject.put("nationality", nationality) }
            countryofresidence?.let { jsonObject.put("countryofresidence", countryofresidence) }
            birthday?.let { jsonObject.put("birthday", birthday) }
            address?.let { jsonObject.put("address", address) }
            zipcode?.let { jsonObject.put("ZIPcode", zipcode) }
            city?.let { jsonObject.put("city", city) }
            email?.let { jsonObject.put("email", email) }
            photo?.let { jsonObject.put("photo", photo) }
            telnum?.let { jsonObject.put("telephone", telnum) }
            jobinfo?.let { jsonObject.put("jobinfo", jobinfo) }
            income?.let { jsonObject.put("income", income) }

            val hparams: Map<String, String>
            if (userid != null) {
                hparams = netHelper.authorizationToken(token)
            } else {
                val h = HashMap<String, String>()
                h.put("Authorization", "Bearer " + phonetoken)
                hparams = h
            }

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    hparams,
                    { response ->

                        val userprofile = UserProfileReply(
                                response.getString("userid"),
                                response.optString("tokenuser"))

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
            log.error(TAG, "profile", e)
            request = null
        }

        return request
    }

    fun getDocuments(token: String, userid: String, completion: (ArrayList<UserDocuments?>?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/1.0/users/$userid/documents")

        var request: IRequest<*>?
        try {

            val r = requestProvider.jsonArrayRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token),
                    { response: JSONArray ->
                        val documents = ArrayList<UserDocuments?>()

                        for(i in 0 until response.length()){

                            val jo = response.getJSONObject(i)
                            val pages = jo.getJSONArray("pages")
                            val docs = mutableListOf<String?>()

                            for (j in 0 until pages.length()) {
                                docs.add(pages.getString(j))
                            }

                            val userdoc = UserDocuments(userid, jo.getString("doctype"), docs.toTypedArray())
                            documents.add(userdoc)
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
                  userid: String,
                  doctype: String,
                  documents: Array<String?>,
                  idempotency: String?,
                  completion: (Boolean?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/1.0/users/$userid/documents")

        var request : IRequest<*>?

        try {
            val ja = JSONArray()
            for(i in 0 until documents.size){
                ja.put(documents[i])
            }

            val jsonObject = JSONObject()
            jsonObject.put("doctype", doctype)
            jsonObject.put("pages", ja)
            jsonObject.putOpt("idempotency", idempotency)

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject, netHelper.authorizationToken(token), { response ->
                completion(true, null)
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

    fun lightdata(token: String,
                  lightData: UserLightData,
                  completion: (UserProfileReply?, Exception?) -> Unit) {

        profile(token = token,
                userid = lightData.userid,
                name = lightData.name,
                surname = lightData.surname,
                nationality = lightData.nationality,
                birthday = lightData.birthday,
                completion = completion)

    }

    fun contacts(token: String,
                 contacts: UserContacts,
                 completion: (UserProfileReply?, Exception?) -> Unit) {

        profile(token = token,
                userid = contacts.userid,
                email = contacts.email,
                completion = completion)
    }

    fun residential(token: String,
                    residential: UserResidential,
                    completion: (UserProfileReply?, Exception?) -> Unit) {

        profile(token = token,
                userid = residential.userid,
                address = residential.address,
                zipcode = residential.ZIPcode,
                city = residential.city,
                countryofresidence = residential.countryofresidence,
                completion = completion)
    }

    fun jobInfo(token: String,
                jobInfo: UserJobInfo,
                completion: (UserProfileReply?, Exception?) -> Unit) {

        profile(token = token,
                userid = jobInfo.userid,
                jobinfo = jobInfo.jobinfo,
                income = jobInfo.income,
                completion = completion)
    }

}