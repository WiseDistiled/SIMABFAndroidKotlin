package com.tiramakan.simabf.events


import com.tiramakan.simabf.core.ApiError

/**
 * Error that is posted when a non-network error event occurs in the [retrofit.Retrofit]
 */
class RestAdapterErrorEvent(val cause: ApiError)
