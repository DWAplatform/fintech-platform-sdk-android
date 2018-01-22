package com.dwaplatform.android.enterprise.api

import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.enterprise.models.EnterpriseAddress
import com.dwaplatform.android.enterprise.models.EnterpriseContacts
import com.dwaplatform.android.enterprise.models.EnterpriseInfo
import com.dwaplatform.android.enterprise.models.EnterpriseProfile
import com.dwaplatform.android.log.Log
import org.json.JSONObject
import java.net.URLEncoder
import java.util.HashMap
import javax.inject.Inject

class EnterpriseProfileAPI @Inject constructor(internal val hostName: String,
                                               internal val queue: IRequestQueue,
                                               internal val requestProvider: IRequestProvider,
                                               internal val log: Log,
                                               val netHelper: NetHelper) {
    private val TAG = "EnterpriseProfileAPI"

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
            log.error(TAG, "searchUser", e)
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

                        val userprofile = EnterpriseProfile( userid=
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
                userid = info.userid,
                name = info.name,
                enterpriseType = info.enterpriseType,
                completion = completion)

    }

    fun contacts(token: String,
                 contacts: EnterpriseContacts,
                 completion: (EnterpriseProfile?, Exception?) -> Unit) {

        enterprise(token = token,
                userid = contacts.userid,
                email = contacts.email,
                telephone = contacts.telephone,
                completion = completion)
    }

    fun address(token: String,
                address: EnterpriseAddress,
                completion: (EnterpriseProfile?, Exception?) -> Unit) {

        enterprise(token = token,
                userid = address.userid,
                address = address.address,
                zipcode = address.postalCode,
                city = address.city,
                countryHeadquarters = address.country,
                completion = completion)
    }
}