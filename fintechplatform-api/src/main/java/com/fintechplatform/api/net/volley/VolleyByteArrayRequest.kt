package com.fintechplatform.api.net.volley

import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.fintechplatform.api.net.IRequest


open class VolleyByteArrayRequest(method: Int,
                             url: String,
                             payload: ByteArray,
                             header: Map<String, String>,
                             listener: (String?) -> Unit,
                             errorListener: (VolleyError) -> Unit)
    : ByteArrayRequestStringResponse(method, url, payload, Response.Listener(listener), errorListener), IRequest<String>{
    override fun setIRetryPolicy(retryPolicy: RetryPolicy): IRequest<String> {
        setIRetryPolicy(retryPolicy)
        return this
    }
}