package com.fintechplatform.api.net.volley

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestQueue

/**
 * Volley request queue implementation
 */
class VolleyRequestQueueProvider(private val requestQueue: RequestQueue) : IRequestQueue {

    override fun <T> add(request: IRequest<T>): IRequest<T> {
        requestQueue.add(request as Request<*>)
        return request
    }
}
