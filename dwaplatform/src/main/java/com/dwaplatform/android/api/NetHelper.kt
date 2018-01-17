package com.dwaplatform.android.api

import com.android.volley.DefaultRetryPolicy
import com.dwaplatform.android.profile.models.UserProfile
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.HashMap

class NetHelper constructor(val hostName: String) {

    inner class GenericCommunicationError(throwable: Throwable) : Exception(throwable)

    inner class IdempotencyError(throwable: Throwable) : Exception(throwable)

    inner class TokenError(throwable: Throwable) : Exception(throwable)

    inner class ReplyParamsUnexpected(throwable: Throwable) : Exception(throwable)


    val PROTOCOL_CHARSET = "utf-8"

    fun getURL(path: String): String {
        if(hostName.startsWith("http://") || hostName.startsWith("https://")){
            return "$hostName$path"
        } else {
            return "https://$hostName$path"
        }
    }

    val defaultpolicy = DefaultRetryPolicy(
            30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

    fun authorizationToken(token: String): Map<String, String> {
        val header = HashMap<String, String>()
        header.put("Authorization", "Bearer $token")
        return header
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
}