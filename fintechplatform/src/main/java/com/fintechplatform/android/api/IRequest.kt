package com.fintechplatform.android.api

import com.android.volley.RetryPolicy

/**
 * Base Interface for all json requests
 */
interface IRequest<T> {
    fun cancel()
    fun setIRetryPolicy(retryPolicy: RetryPolicy): IRequest<T>
}
