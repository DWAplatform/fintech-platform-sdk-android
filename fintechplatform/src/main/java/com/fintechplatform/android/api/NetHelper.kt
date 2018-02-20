package com.fintechplatform.android.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.VolleyError
import com.fintechplatform.android.card.api.PaymentCardRestAPI
import com.fintechplatform.android.enterprise.models.EnterpriseProfile
import com.fintechplatform.android.profile.models.UserProfile
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*




class NetHelper constructor(val hostName: String) {

    inner class HeaderBuilder {
        private var header = HashMap<String, String>()

        fun authorizationToken(token: String): HeaderBuilder {
            header.put("Authorization", "Bearer $token")
            return this
        }

        fun idempotency(idempotencyKey: String): HeaderBuilder {
            header.put("Idempotency-Key", idempotencyKey)
            return this
        }

        fun getHeaderMap(): HashMap<String, String> { return header }

    }

    inner class GenericCommunicationError(throwable: Throwable) : Exception(throwable)

    inner class IdempotencyError(throwable: Throwable) : Exception(throwable)

    inner class TokenError(throwable: Throwable) : Exception(throwable)

    inner class ReplyParamsUnexpected(throwable: Throwable) : Exception(throwable)

    inner class UserNotFound(throwable: Throwable) : Exception(throwable)

    inner class EnterpriseNotFound(throwable: Throwable) : Exception(throwable)

    val PROTOCOL_CHARSET = "utf-8"

    fun getURL(path: String): String {
        if(hostName.startsWith("http://") || hostName.startsWith("https://")){
            return "$hostName$path"
        } else {
            return "https://$hostName$path"
        }
    }

    fun getHeaderBuilder(): HeaderBuilder = HeaderBuilder()

    val defaultpolicy = DefaultRetryPolicy(
            30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

    fun authorizationToken(token: String): Map<String, String> {
        return getHeaderBuilder().authorizationToken(token).getHeaderMap()
    }

    fun createRequestError(volleyError: VolleyError): PaymentCardRestAPI.APIReplyError {

        try {
            val response: NetworkResponse = volleyError.networkResponse
            val jsonString = String(response.data, Charset.forName(PROTOCOL_CHARSET))

            return PaymentCardRestAPI.APIReplyError(JSONArray(jsonString), volleyError)
        } catch(e: JSONException) {
            return PaymentCardRestAPI.APIReplyError(null, volleyError)
        }
    }

    fun getPathFromAccountType(accountType: String): String {
        return if (accountType == "PERSONAL"){
                "users"
            } else {
                "enterprises"
            }
    }

    @Throws(UnsupportedEncodingException::class)
    fun getUrlDataString(url: String, params: HashMap<String, Any>): String {

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

    data class UserReplyParserResult(val userprofile: UserProfile?, val error: Exception?)
    fun searchUserReplyParser(response: JSONObject) : UserReplyParserResult {

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

    fun searchEnterpriseReplyParser(response: JSONObject) : EnterpriseProfile {
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