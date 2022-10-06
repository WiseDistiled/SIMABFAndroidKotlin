package com.tiramakan.simabf.events

import com.tiramakan.simabf.core.ApiError


/**
 * The event that is posted when a network error event occurs.
 * TODO: Consume this event in the [com.tiramakan.simabf.ui.BootstrapActivity] and
 * show a dialog that something went wrong.
 */
class NetworkErrorEvent(val cause: ApiError)
