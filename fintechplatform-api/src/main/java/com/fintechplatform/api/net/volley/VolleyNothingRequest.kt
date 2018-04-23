package com.fintechplatform.api.net.volley

import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.fintechplatform.api.net.IRequest

open class VolleyNothingRequest(method: Int,
                                url: String,
                                errorListener: (VolleyError) -> Unit)
    : RequestNothingReply(method, url,
        Response.ErrorListener(errorListener)), IRequest<Nothing> {

    override fun setIRetryPolicy(retryPolicy: RetryPolicy): IRequest<Nothing> {
        setRetryPolicy(retryPolicy)
        return this
    }
}