package com.fintechplatform.api.net

/**
 * Request Queue Interface
 */
interface IRequestQueue {
    fun <T> add(request: IRequest<T>): IRequest<T>
}
