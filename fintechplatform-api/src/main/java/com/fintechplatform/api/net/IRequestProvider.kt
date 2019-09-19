package com.fintechplatform.api.net

import com.android.volley.VolleyError
import com.fintechplatform.api.net.volley.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * Base Interface for all json requests
 */
interface IRequestProvider {

    fun jsonArrayRequest(method: Int,
                         url: String,
                         jsonRequest: JSONArray?,
                         listener: (JSONArray) -> Unit,
                         errorListener: (VolleyError) -> Unit): VolleyJsonArrayRequest

    fun jsonArrayRequest(method: Int,
                         url: String,
                         jsonRequest: JSONArray?,
                         header: Map<String, String>,
                         listener: (JSONArray) -> Unit,
                         errorListener: (VolleyError) -> Unit): VolleyJsonArrayRequest

    fun jsonObjectRequest(method: Int,
                          url: String,
                          jsonRequest: JSONObject?,
                          listener: (JSONObject) -> Unit,
                          errorListener: (VolleyError) -> Unit): VolleyJsonObjectRequest

    fun jsonObjectRequest(method: Int,
                          url: String,
                          jsonRequest: JSONObject?,
                          header: Map<String, String>,
                          listener: (JSONObject) -> Unit,
                          errorListener: (VolleyError) -> Unit): VolleyJsonObjectRequest


    fun stringRequest(method: Int,
                      url: String,
                      params: Map<String, String>,
                      header: Map<String, String>,
                      listener: (String) -> Unit,
                      errorListener: (VolleyError) -> Unit): VolleyStringRequest

    fun jsonObjectRequestArrayReply(method: Int,
                                    url: String,
                                    jsonRequest: JSONObject?,
                                    header: Map<String, String>,
                                    listener: (JSONArray) -> Unit,
                                    errorListener: (VolleyError) -> Unit): VolleyJsonObjectRequestArrayReply

    fun nothingRequest(method: Int,
                       url: String,
                       header: Map<String, String>,
                       listener: (Nothing?) -> Unit,
                       errorListener: (VolleyError) -> Unit): VolleyNothingRequest

    fun byteArrayRequest(method: Int,
                         url: String,
                         picture: ByteArray,
                         header: Map<String, String>,
                         listener: (String?) -> Unit,
                         errorListener: (VolleyError) -> Unit): VolleyByteArrayRequest

    fun byteArrayResponse(method: Int,
                          url: String,
                          header: Map<String, String>,
                          listener: (ByteArray?) -> Unit,
                          errorListener: (VolleyError) -> Unit): VolleyRequestsByteArrayResponse
}
