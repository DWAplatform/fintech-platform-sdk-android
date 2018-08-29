package com.fintechplatform.api.net

import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*


open class NetHelper constructor(val hostName: String) {

    inner class HeaderBuilder {
        private var header = HashMap<String, String>()

        fun authorizationToken(token: String): HeaderBuilder {
            header.put("Authorization", "Bearer $token")
            return this
        }

        fun idempotency(idempotencyKey: String?): HeaderBuilder {
            idempotencyKey?.let { header.put("Idempotency-Key", idempotencyKey) }
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

    /**
     * Represent the error response from Fintech Platform API.
     *
     * [errors] error as a list of Error, could be null in case of json parsing error
     * [throwable] error returned from the underlying HTTP library
     */
    data class APIResponseError(val errors: List<Error>?, val throwable: Throwable?) : Exception(throwable) {

        //TODO: extend message: if list not empty => print error else throwable.message

        override val message: String?
            get() {
                val errors = errors?.let {
                    it.map {
                        it.toString()
                    }.toString()
                }
                return errors ?: super.message
            }

    }

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

    fun createRequestError(volleyError: VolleyError): Exception {
        try {
            val response: NetworkResponse? = volleyError.networkResponse
            response?.let {
                val jsonString = String(response.data, Charset.forName(PROTOCOL_CHARSET))

                val arrayJson = JSONArray(jsonString)

                val listError = (0 until arrayJson.length())
                        .map {
                            val optJsonObj = arrayJson.get(it) as? JSONObject
                            optJsonObj ?: return GenericCommunicationError(volleyError)
                        }
                        .map {
                            val rep =
                                    try {
                                        Pair(ErrorCode.valueOf(it.getString("code")), it.getString("message"))
                                    } catch (x: Exception) {
                                        Pair(ErrorCode.unknown_error, "[${it.getString("code")}] ${it.getString("message")}")
                                    }
                            Error(rep.first, rep.second)
                        }
                        .toList()

                return APIResponseError(listError, volleyError)

            }?: return GenericCommunicationError(volleyError)
        } catch(e: JSONException) {
            return GenericCommunicationError(volleyError)
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

}