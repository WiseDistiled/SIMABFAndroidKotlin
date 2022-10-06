package com.tiramakan.simabf.bootstrap

import android.os.Handler
import android.os.Looper

import com.squareup.otto.Bus
import com.squareup.otto.ThreadEnforcer

//import timber.log.Timber;

/**
 * This message bus allows you to post a message from any thread and it will get handled and then
 * posted to the main thread for you.
 */
class PostFromAnyThreadBus : Bus(ThreadEnforcer.MAIN) {

    override fun post(event: Any) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            // We're not in the main loop, so we need to get into it.
            Handler(Looper.getMainLooper()).post {
                // We're now in the main loop, we can post now
                super@PostFromAnyThreadBus.post(event)
            }
        } else {
            super.post(event)
        }
    }

    override fun unregister(`object`: Any) {
        //  Lots of edge cases with register/unregister that sometimes throw.
        try {
            super.unregister(`object`)
        } catch (e: IllegalArgumentException) {
            // Timber.e(e, e.getMessage());
        }

    }
}
