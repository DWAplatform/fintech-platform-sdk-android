package com.fintechplatform.android.api.volley

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.fintechplatform.android.api.IRequest
import com.fintechplatform.android.api.IRequestQueue

/**
 * Volley request queue implementation
 */
class VolleyRequestQueueProvider(private val requestQueue: RequestQueue) : IRequestQueue {

    override fun <T> add(request: IRequest<T>): IRequest<T> {
        requestQueue.add(request as Request<*>)
        return request
    }
}
