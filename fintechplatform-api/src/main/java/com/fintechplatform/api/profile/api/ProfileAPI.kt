package com.fintechplatform.api.profile.api

import com.android.volley.Request
import com.fintechplatform.api.iban.models.UserResidential
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.profile.models.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

/**
 * ProfileAPI class performs request to get and updates user profile informations
 */
class ProfileAPI @Inject constructor(
        val restAPI: IdsDocumentsAPI,
        internal val hostName: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val log: Log,
        val netHelper: NetHelper) {

    private val TAG = "ProfileAPI"
    /**
     * Get informations about User [userid] from Fintech Platform identified from [tenantId].
     * use [token] got from "Create User token" request.
     */
    fun searchUser(token: String, userid: String, tenantId: String?, completion: (UserProfile?, Exception?) -> Unit): IRequest<*>? {

        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/users/$userid")

        var request: IRequest<*>?
        try {

            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token),
                    { response ->
                        val (userprofile, error) = searchUserReplyParser(response)
                        completion(userprofile, error)
                    })
            {error ->
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 ->
                        completion(null, netHelper.TokenError(error))
                    404 -> completion(null, netHelper.UserNotFound(error))
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
                h.put("Authorization", "Bearer " + token)
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
                val status = if (error.networkResponse != null) error.networkResponse.statusCode
                else -1
                when (status) {
                    401 ->
                        completion(null, netHelper.TokenError(error))
                    404 -> completion(null, netHelper.UserNotFound(error))
                    else ->
                        completion(null, netHelper.GenericCommunicationError(error))
                }
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "profileAPI", e)
            request = null
        }

        return request
    }

    /**
     * Get User [userId] documents form Fintech Platform [tenantId]
     * use [token] got from "Create User token" request.
     * [completion] returns list of documents uploaded to Fintech Platform
     */
    fun getDocuments(token: String, userId: String, tenantId: String, completion: (List<UserDocuments?>?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/$tenantId/users/$userId/documents/")

        var request: IRequest<*>?
        try {

            val r = requestProvider.jsonArrayRequest(Request.Method.GET, url,
                    null, netHelper.authorizationToken(token),
                    { response: JSONArray ->
                        val documents = mutableListOf<UserDocuments?>()

                        for(i in 0 until response.length()){

                            val jo = response.getJSONObject(i)
                            val ja = jo.optJSONArray("bucketObjectIdPages")
                            val bucketObjectIdPages = ja?.run {
                                val bucketObjIds = mutableListOf<String?>()
                                for (j in 0 until this.length()) {
                                    bucketObjIds.add(this.getString(j))
                                }
                                bucketObjIds.filterNotNull()
                            }
                            val docType = jo.optString("doctype")?.run{
                                DocType.valueOf(this)
                            }

                            val userdoc = UserDocuments(userId, jo.getString("documentId"), docType, bucketObjectIdPages)
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

    /**
     * Send User [userId] IDs document to Fintech Platform, identified from [tenantId].
     * use [token] got from "Create User token" request.
     * [doctype] type of document uploaded [IDENTITY_CARD, PASSPORT, DRIVING_LICENCE ]
     * [documents] Array of Base 64 encoded image uploaded.
     * Use [idempotency] parameter to avoid multiple inserts.
     * [completion] return document UUID identifier.
     */
    fun documents(token: String,
                  userId: String,
                  tenantId: String,
                  fileName: String?=null,
                  doctype: String,
                  documentPages: ArrayList<ByteArray>,
                  idempotency: String,
                  completion: (UserDocuments?, Exception?) -> Unit) {

        val objectIds = mutableListOf<String>()

        documentPages.forEach { pageImage ->
            restAPI.addBucketForUserDocuments(token, tenantId, userId, fileName) { optBucketObject, optError ->
                optError?.let{ error -> completion(null, error); return@addBucketForUserDocuments}
                optBucketObject?.let { bucketObject ->
                    bucketObject.uploadPath?.let { path ->
                        restAPI.uploadBucketObjectFile(token, path, pageImage) { optString, optError ->
                            optError?.let{ error -> completion(null, error); return@uploadBucketObjectFile}
                            optString?.let {
                                objectIds.add(bucketObject.objectId)
                            }
                        }
                    }
                }
            }
        }

        if(objectIds.isNotEmpty()) {
            restAPI.createDocuments(token, userId, tenantId, doctype, objectIds, idempotency) { optUserDocuments, optError ->
                optError?.let{ error -> completion(null, error); return@createDocuments}
                optUserDocuments?.let{
                    completion(it, null)
                }
            }
        }
    }

    /**
     * Update User informations, identified from [lightData] object (name, surname, birthday, nationality)
     */
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

    /**
     * Update User informations, identified from [contacts] object (email and telephone)
     */
    fun contacts(token: String,
                 contacts: UserContacts,
                 completion: (UserProfileReply?, Exception?) -> Unit) {

        editProfile(token = token,
                userid = contacts.userid,
                tenantId = contacts.tenantId,
                email = contacts.email,
                completion = completion)
    }

    /**
     * Update User [residential] informations (address, zipcode, city and country)
     */
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

    /**
     * Update User [jobInfo] informations (jobInfo and income)
     */
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


    data class UserReplyParserResult(val userprofile: UserProfile?, val error: Exception?)
    private fun searchUserReplyParser(response: JSONObject) : UserReplyParserResult {

        val userprofile = UserProfile(
                response.getString("userId"),
                response.optString("name"),
                response.optString("surname"),
                response.optString("nationality"),
                response.optString("birthday"),
                response.optString("addressOfResidence"),
                response.optString("postalCode"),
                response.optString("cityOfResidence"),
                response.optString("telephone"),
                response.optString("email"),
                response.optString("photo"),
                response.optString("countryOfResidence"),
                response.optString("occupation"),
                response.optString("incomeRange"),
                null)

        return UserReplyParserResult(userprofile, null)

    }


}