package com.fintechplatform.api.net.volley

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser

/**
 * Volley implementation of IRequest for json JsonObject request and jsonarray reply
 */
open class RequestNothingReply(method: Int, url: String, private val listener: Response.Listener<Nothing?>, errorListener: Response.ErrorListener)
    : Request<Nothing?>(method, url, errorListener) {

    override fun deliverResponse(response: Nothing?) {
        listener.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<Nothing?> {
        return Response.success(null, HttpHeaderParser.parseCacheHeaders(response))
    }
}