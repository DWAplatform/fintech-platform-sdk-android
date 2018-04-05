package com.fintechplatform.android.net

/**
 * Request Queue Interface
 */
interface IRequestQueue {
    fun <T> add(request: IRequest<T>): IRequest<T>
}
