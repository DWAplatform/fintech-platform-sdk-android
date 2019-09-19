package com.fintechplatform.api.net.volley

import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.fintechplatform.api.net.IRequest


open class VolleyRequestsByteArrayResponse(method: Int,
                                      url: String,
                                      listener: (ByteArray?) -> Unit,
                                      errorListener: (VolleyError) -> Unit)
    : RequestByteArrayResponse(method, url,
        Response.Listener(listener),
        Response.ErrorListener(errorListener)), IRequest<ByteArray> {

    override fun setIRetryPolicy(retryPolicy: RetryPolicy): IRequest<ByteArray> {
        setRetryPolicy(retryPolicy)
        return this
    }
}