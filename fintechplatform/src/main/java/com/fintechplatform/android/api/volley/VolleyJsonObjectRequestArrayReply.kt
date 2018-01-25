package com.fintechplatform.android.api.volley

import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.fintechplatform.android.api.IRequest
import org.json.JSONArray
import org.json.JSONObject

/**
 * Volley implementation of IRequest for json JSONArray response
 */
open class VolleyJsonObjectRequestArrayReply(method: Int, url: String,
                                             jsonRequest: JSONObject?,
                                             listener: (JSONArray) -> Unit,
                                             errorListener: (VolleyError) -> Unit)
    : JsonObjectRequestArrayReply(method, url, jsonRequest, listener, errorListener), IRequest<JSONArray> {

    override fun setIRetryPolicy(retryPolicy: RetryPolicy): IRequest<JSONArray> {
        setRetryPolicy(retryPolicy)
        return this
    }
}
