package com.dwaplatform.android.profile.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.iban.models.UserResidential
import com.dwaplatform.android.log.Log
import com.dwaplatform.android.profile.models.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject


class ProfileAPI @Inject constructor(
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log) {

    private val defaultpolicy = DefaultRetryPolicy(
            30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

    private val TAG = "ProfileAPI"


    inner class GenericCommunicationError(throwable: Throwable) : Exception(throwable)

    inner class IdempotencyError(throwable: Throwable) : Exception(throwable)

    private fun getURL(path: String): String {
        if (hostName.startsWith("http://") || hostName.startsWith("https://")){
            return "$hostName$path"
        } else {
            return "https://$hostName$path"
        }
    }

    private fun authorizationToken(token: String): Map<String, String> {
        val header = HashMap<String, String>()
        header.put("Authorization", "Bearer $token")
        return header
    }

    data class UserReplyParserResult(val userprofile: UserProfile?, val error: Exception?)
    fun searchUserReplyParser(response: JSONObject) : UserReplyParserResult {

        val userprofile = UserProfile(
                response.getString("userid"),
                response.optString("name"),
                response.optString("surname"),
                response.optString("nationality"),
                response.optString("birthday"),
                response.optString("address"),
                response.optString("ZIPcode"),
                response.optString("city"),
                response.optString("telephone"),
                response.optString("email"),
                response.optString("photo"),
                response.optString("countryofresidence"),
                response.optString("jobinfo"),
                response.optString("income"),
                response.optString("tokenuser"))

        return UserReplyParserResult(userprofile, null)

    }
    /*
    fun searchUser(telephone: String,
                   phonetoken: String,
                   completion: (UserProfile?, Exception?) -> Unit): IRequest<*>? {
        val encodedTelephone = URLEncoder.encode(telephone, "UTF-8")
        val url = getURL("/rest/v1/telephonenumbers/$encodedTelephone/user")

        var request: IRequest<*>?
        try {
            val hparams = HashMap<String, String>()
            hparams.put("Authorization", "Bearer " + phonetoken)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url, null, hparams,
                    { response ->
                        val (userprofile, error) = searchUserReplyParser(response)
                        completion(userprofile, error)
                    })
            {error ->
                // user not found
                if (error.networkResponse.statusCode == 403) {
                    completion(null, null)
                    return@jsonObjectRequest
                }

                completion(null, error) }
            r.setIRetryPolicy(defaultpolicy)
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
        val url = getURL("/rest/v1/users/$encodedUserId/profile")

        var request: IRequest<*>?
        try {
            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url,
                    null, authorizationToken(token),
                    { response ->
                        val (userprofile, error) = searchUserReplyParser(response)
                        completion(userprofile, error)
                    })
            {error ->
                completion(null, error) }
            r.setIRetryPolicy(defaultpolicy)
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


        val url = getURL("/rest/1.0/user/profile")

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
                hparams = authorizationToken(token)
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
            {error ->
                completion(null, error) }
            r.setIRetryPolicy(defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "phoneCodeVerify", e)
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

        val url = getURL("/rest/1.0/users/$userid/documents")

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

            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject, authorizationToken(token), { response ->
                completion(true, null)
            }) { error ->
                val status = if (error.networkResponse != null)
                    error.networkResponse.statusCode else -1
                when (status) {
                    409 -> {
                        completion(null, IdempotencyError(error))
                    }
                    else -> completion(null, GenericCommunicationError(error))
                }
            }

            r.setIRetryPolicy(defaultpolicy)
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