package com.fintechplatform.api.net.volley

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


open class ByteArrayRequestStringResponse(method: Int,
                                          url: String,
                                          val payload: ByteArray,
                                          val listener: Response.Listener<String?>,
                                          errorListener: (VolleyError) -> Unit) : Request<String?>(method, url, errorListener) {

    /**
     * Subclasses must implement this to parse the raw network response
     * and return an appropriate response type. This method will be
     * called from a worker thread.  The response will not be delivered
     * if you return null.
     * @param response Response from the network
     * @return The parsed response, or null in the case of an error
     */
    override fun parseNetworkResponse(response: NetworkResponse): Response<String?> {
        try {
            val responseString = String(response.data,
                    Charset.forName(HttpHeaderParser.parseCharset(response.headers)))
            return Response.success(responseString,
                    HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            return Response.error<String>(ParseError(e))
        } catch (je: IOException) {
            return Response.error<String>(ParseError(je))
        }
    }

    /**
     * Subclasses must implement this to perform delivery of the parsed
     * response to their listeners.  The given response is guaranteed to
     * be non-null; responses that fail to parse are not delivered.
     * @param response The parsed response returned by
     * [.parseNetworkResponse]
     */
    override fun deliverResponse(response: String?) {
        listener.onResponse(response)
    }

    override fun getBody(): ByteArray {
        return payload
    }
}