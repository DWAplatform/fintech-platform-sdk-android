package com.fintechplatform.api.net.volley

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser

/**
 * Volley implementation of IRequest for json JsonObject request and jsonarray reply
 */
open class RequestNothingReply
/**
 * Creates a new request.
 * @param url URL to fetch from
 * *
 * @param listener Listener to receive the response
 * *
 * @param errorListener Error listener, or null to ignore errors.
 */(method: Int, url: String, errorListener: Response.ErrorListener) : Request<Nothing>(method, url, errorListener) {

    override fun deliverResponse(response: Nothing?) {

    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<Nothing> {
        return Response.success(null, HttpHeaderParser.parseCacheHeaders(response))
    }
}