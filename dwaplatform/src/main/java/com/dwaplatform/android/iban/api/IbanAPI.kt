package com.dwaplatform.android.iban.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.iban.models.BankAccount
import com.dwaplatform.android.log.Log
import com.dwaplatform.android.profile.models.UserProfileReply
import org.json.JSONArray
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.HashMap
import javax.inject.Inject

class IbanAPI @Inject constructor(internal val hostName: String,
                                  internal val queue: IRequestQueue,
                                  internal val requestProvider: IRequestProvider,
                                  internal val log: Log

){
    inner class GenericCommunicationError(throwable: Throwable) : Exception(throwable)

    private val TAG = "IbanAPI"

    private val PROTOCOL_CHARSET = "utf-8"

    private fun getURL(path: String): String {
        if(hostName.startsWith("http://") || hostName.startsWith("https://")){
            return "$hostName$path"
        } else {
            return "https://$hostName$path"
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getUrlDataString(url: String, params: HashMap<String, Any>): String {

        val result = StringBuilder()
        var first = true
        result.append(url)
        for ((key, value) in params) {
            if (first) {
                result.append("?")
                first = false
            } else
                result.append("&")

            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value.toString(), "UTF-8"))
        }

        return result.toString()
    }

    private val defaultpolicy = DefaultRetryPolicy(
            30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

    private fun authorizationToken(token: String): Map<String, String> {
        val header = HashMap<String, String>()
        header.put("Authorization", "Bearer $token")
        return header
    }

    fun createIBAN(token: String,
                   userid: String,
                   iban: String,
                   completion: (BankAccount?, Exception?) -> Unit): IRequest<*>? {
        val url = getURL("/rest/1.0/fin/iban/create")

        var request: IRequest<*>?
        try {
            val jsonObject = JSONObject()
            jsonObject.put("userid", userid)
            jsonObject.put("iban", iban)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonObjectRequest(Request.Method.POST, url, jsonObject,
                    authorizationToken(token), { response ->

                val ibanid = response.getString("ibanid")
                val iban = response.getString("iban")
                val activestate = response.getString("activestate")

                completion(BankAccount(ibanid, iban, activestate), null)
            }) { error ->
                completion(null, GenericCommunicationError(error))
            }

            r.setIRetryPolicy(defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "createIBAN", e)
            request = null
        }

        return request
    }

    fun residenceProfile(token: String,
                         userid: String,
                         countryofresidence: String? = null,
                         address: String? = null,
                         zipcode: String? = null,
                         city: String? = null,
                         phonetoken: String? = null,
                         completion: (UserProfileReply?, Exception?) -> Unit): IRequest<*>? {


            val url = getURL("/rest/1.0/user/profile")

            var request: IRequest<*>?
            try {
                val jsonObject = JSONObject()
                jsonObject.put("userid", userid)
                if (countryofresidence != null) jsonObject.put("countryofresidence",
                        countryofresidence)
                if (address != null) jsonObject.put("address", address)
                if (zipcode != null) jsonObject.put("ZIPcode", zipcode)
                if (city != null) jsonObject.put("city", city)


                var hparams: Map<String, String> = authorizationToken(token)
                if (userid == null) {
                    val h = HashMap<String, String>()
                    h.put("Authorization", "Bearer " + phonetoken)
                    hparams = h
                }

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

    fun getbankAccounts(token: String,
                        userid: String,
                        completion: (List<BankAccount>?, Exception?) -> Unit): IRequest<*>? {

        val baseurl = getURL("/rest/1.0/fin/iban/list")

        var request: IRequest<*>?
        try {
            val params = HashMap<String, Any>()
            params.put("userid", userid)
            val url = getUrlDataString(baseurl, params)

            // Request a string response from the provided URL.
            val r = requestProvider.jsonArrayRequest(Request.Method.GET, url,
                    null, authorizationToken(token),
                    { response: JSONArray ->

                        val bas = IntArray(response.length()) {i -> i}.map { i ->
                            val reply = response.getJSONObject(i)

                            BankAccount(
                                    reply.optString("ibanid"),
                                    reply.optString("iban"),
                                    reply.optString("activestate"))
                        }

                        completion(bas, null)
                    })
            {error ->
                completion(null, error) }
            r.setIRetryPolicy(defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "getbankAccounts", e)
            request = null
        }

        return request
    }
}