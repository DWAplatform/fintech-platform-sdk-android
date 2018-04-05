package com.fintechplatform.android.net

import com.android.volley.VolleyError
import com.fintechplatform.android.net.volley.VolleyJsonArrayRequest
import com.fintechplatform.android.net.volley.VolleyJsonObjectRequest
import com.fintechplatform.android.net.volley.VolleyJsonObjectRequestArrayReply
import com.fintechplatform.android.net.volley.VolleyStringRequest
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
}
