package com.fintechplatform.android.api

/**
 * Request Queue Interface
 */
interface IRequestQueue {
    fun <T> add(request: IRequest<T>): IRequest<T>
}
