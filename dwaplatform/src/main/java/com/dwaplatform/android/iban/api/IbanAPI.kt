package com.dwaplatform.android.iban.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.dwaplatform.android.api.IRequest
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.iban.models.BankAccount
import com.dwaplatform.android.log.Log
import org.json.JSONObject
import java.util.HashMap
import javax.inject.Inject

/**
 * Created by ingrid on 04/01/18.
 */
class IbanAPI @Inject constructor(internal val hostName: String,
                                  internal val queue: IRequestQueue,
                                  internal val requestProvider: IRequestProvider,
                                  internal val log: Log

){
    inner class GenericCommunicationError(throwable: Throwable) : Exception(throwable)

    inner class IdempotencyError(throwable: Throwable) : Exception(throwable)

    private val TAG = "PayInAPI"

    private val PROTOCOL_CHARSET = "utf-8"

    private fun getURL(path: String): String {
        if(hostName.startsWith("http://") || hostName.startsWith("https://")){
            return "$hostName$path"
        } else {
            return "https://$hostName$path"
        }
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
}