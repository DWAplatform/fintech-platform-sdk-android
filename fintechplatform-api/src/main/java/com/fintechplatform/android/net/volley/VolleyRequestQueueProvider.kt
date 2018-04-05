package com.fintechplatform.android.net.volley

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.fintechplatform.android.net.IRequest
import com.fintechplatform.android.net.IRequestQueue

/**
 * Volley request queue implementation
 */
class VolleyRequestQueueProvider(private val requestQueue: RequestQueue) : IRequestQueue {

    override fun <T> add(request: IRequest<T>): IRequest<T> {
        requestQueue.add(request as Request<*>)
        return request
    }
}
