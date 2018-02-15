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

    fun searchUser(token: String, userid: String, tenantId: String?, completion: (UserProfile?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/users/$userid")

        var request: IRequest<*>?
        try {

            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token),
                    { response ->
                        val (userprofile, error) = netHelper.searchUserReplyParser(response)
                        completion(userprofile, error)
                    })
            {error ->
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


    private fun editProfile(token: String,
                            userid: String? = null,
                            tenantId: String?=null,
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
                            pin: String?=null,
                            completion: (UserProfileReply?, Exception?) -> Unit): IRequest<*>? {


        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/users/$userid")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            if (pin != null) jsonObject.put("pin", pin)
            if (userid != null) jsonObject.put("userid", userid)
            if (name != null) jsonObject.put("name", name)
            if (surname != null) jsonObject.put("surname", surname)
            if (nationality != null) jsonObject.put("nationality", nationality)
            if (countryofresidence != null) jsonObject.put("countryOfResidence", countryofresidence)
            if (birthday != null) jsonObject.put("birthday", birthday)
            if (address != null) jsonObject.put("addressOfResidence", address)
            if (zipcode != null) jsonObject.put("postalCode", zipcode)
            if (city != null) jsonObject.put("cityOfResidence", city)
            if (email != null) jsonObject.put("email", email)
            if (photo != null) jsonObject.put("photo", photo)
            if (telnum != null) jsonObject.put("telephone", telnum)
            if (jobinfo != null) jsonObject.put("occupation", jobinfo)
            if (income != null) jsonObject.put("incomeRange", income)

            val hparams: Map<String, String>
            if (userid != null) {
                hparams = netHelper.authorizationToken(token)
            } else {
                val h = HashMap<String, String>()
                h.put("Authorization", "Bearer " + phonetoken)
                hparams = h
            }

            val r = requestProvider.jsonObjectRequest(Request.Method.PUT, url, jsonObject,
                    hparams,
                    { response ->

                        val userprofile = UserProfileReply(
                                response.getString("userId"),null)

                        completion(userprofile, null)
                    })
            {error ->
                completion(null, error) }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "profileAPI", e)
            request = null
        }

        return request
    }

    // TODO documents
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

        editProfile(token = token,
                userid = lightData.userid,
                tenantId = lightData.tenantId,
                name = lightData.name,
                surname = lightData.surname,
                nationality = lightData.nationality,
                birthday = lightData.birthday,
                completion = completion)

    }

    fun contacts(token: String,
                 contacts: UserContacts,
                 completion: (UserProfileReply?, Exception?) -> Unit) {

        editProfile(token = token,
                userid = contacts.userid,
                tenantId = contacts.tenantId,
                email = contacts.email,
                completion = completion)
    }

    fun residential(token: String,
                    residential: UserResidential,
                    completion: (UserProfileReply?, Exception?) -> Unit) {

        editProfile(token = token,
                userid = residential.userid,
                tenantId = residential.tenantid,
                address = residential.address,
                zipcode = residential.ZIPcode,
                city = residential.city,
                countryofresidence = residential.countryofresidence,
                completion = completion)
    }

    fun jobInfo(token: String,
                jobInfo: UserJobInfo,
                completion: (UserProfileReply?, Exception?) -> Unit) {

        editProfile(token = token,
                userid = jobInfo.userid,
                tenantId = jobInfo.tenantId,
                jobinfo = jobInfo.jobinfo,
                income = jobInfo.income,
                completion = completion)
    }

}